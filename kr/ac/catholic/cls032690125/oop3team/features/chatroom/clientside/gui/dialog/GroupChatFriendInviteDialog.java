package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.dialog;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.GroupChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.CChatroomInvitePacket;
import kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.CFriendController;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GroupChatFriendInviteDialog extends JDialog {
    private Client client;
    private CChatroomIndividualController controller;
    private CFriendController friendController;
    private GroupChatScreen screen;

    private JPanel friendListPanel;
    private List<UserProfile> friendList = new ArrayList<>();
    private List<JCheckBox> checkBoxes = new ArrayList<>();
    private void addFriends(UserProfile userProfile) {
        if(screen.getMembers().contains(userProfile.getUserId())) return;
        friendList.add(userProfile);
        JCheckBox checkBox = new JCheckBox(userProfile.getName());
        checkBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        checkBoxes.add(checkBox);
        friendListPanel.add(checkBox);
    }

    public GroupChatFriendInviteDialog(Client client, CChatroomIndividualController controller, GroupChatScreen screen) {
        super((JFrame) controller.getScreen(), "친구 초대", true);

        this.client = client;
        this.controller = controller;
        this.friendController = new CFriendController(client);
        this.screen = screen;

        setSize(300, 400);
        setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());

        // 친구 목록
        friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 초대 버튼
        JButton inviteButton = new JButton("초대");
        inviteButton.addActionListener(e -> {
            boolean invited = false;
            ArrayList<String> invs = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                var ckb = checkBoxes.get(i);
                if (ckb.isSelected()) {
                    String friendName = friendList.get(i).getUserId();
                    invs.add(friendName);
                    invited = true;
                }
            }

            if (invited) {
                controller.inviteMember(new CChatroomInvitePacket(controller.getChatroom().getChatroomId(), invs), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                    @Override
                    protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                        GroupChatFriendInviteDialog.this.dispose();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this,
                        "초대할 친구를 선택해주세요.",
                        "알림",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.add(inviteButton, BorderLayout.SOUTH);
        add(panel);
        initiate();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) { initiate(); }
        super.setVisible(visible);
    }

    private void initiate() {
        friendController.getFriendList(client.getCurrentSession().getUserId(), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
            @Override
            protected void execute(ServerResponsePacketSimplefied<UserProfile[]> data) {
                System.out.println(data.getData().length);
                for(var f : data.getData()) {
                    addFriends(f);
                }
                friendListPanel.revalidate();
                friendListPanel.repaint();

                revalidate();
                repaint();
            }
        });
    }
}
