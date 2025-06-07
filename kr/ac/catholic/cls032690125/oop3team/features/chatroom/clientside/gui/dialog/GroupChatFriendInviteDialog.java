package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.dialog;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.GroupChatScreen;
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
    private List<JCheckBox> checkBoxes = new ArrayList<>();
    private void addFriends(UserProfile userProfile) {
        if(screen.getMembers().contains(userProfile.getUserId())) return;
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
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    String userId = (String) checkBox.getClientProperty("userId");
                    screen.getMembers().add(userId);
                    controller.sendMessage("[시스템] " + userId + "님이 초대되었습니다.", new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                        @Override
                        protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                            // 메시지 전송 결과는 무시
                        }
                    });
                    invited = true;
                }
            }
            if (invited) {
                dispose();
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
        super.setVisible(visible);
        if (visible) { initiate(); }
    }

    private void initiate() {
        System.out.println("[초대 다이얼로그] initiate() 진입");
        String userId = client.getCurrentSession() != null ? client.getCurrentSession().getUserId() : "null";
        System.out.println("[초대 다이얼로그] userId: " + userId);
        checkBoxes.clear();
        friendListPanel.removeAll();
        friendController.getFriendList(userId, new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
            @Override
            protected void execute(ServerResponsePacketSimplefied<UserProfile[]> data) {
                System.out.println("[초대 다이얼로그] 친구 목록 수신: " + (data.getData() == null ? "null" : data.getData().length));
                for (var f : data.getData()) {
                    System.out.println("[초대 다이얼로그] 친구: " + f.getName() + " / " + f.getUserId());
                    if (screen.getMembers().contains(f.getUserId())) continue;
                    JCheckBox checkBox = new JCheckBox(f.getName() + " (" + f.getUserId() + ")");
                    checkBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
                    checkBox.putClientProperty("userId", f.getUserId());
                    checkBoxes.add(checkBox);
                    friendListPanel.add(checkBox);
                }
                SwingUtilities.invokeLater(() -> {
                    friendListPanel.revalidate();
                    friendListPanel.repaint();
                });
            }
        });
    }
}
