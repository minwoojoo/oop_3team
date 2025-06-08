package kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.MainScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.CreateGroupChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.GroupChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.PrivateChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.CChatroomCreatePacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomCreatePacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SPrivateChatRoomResponsePacket;
import kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.CFriendController;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendProfileScreen extends JFrame {
    private Client client;
    private String myUserId;
    private UserProfile friend;
    private MainScreen mainScreen;
    private CFriendController friendController;
    private CChatroomController cChatroomController;
    private CFriendController cFriendController;
    private Map<String, String> userIdToName = new HashMap<>();


    public FriendProfileScreen(Client client, String myUserId, UserProfile friend, MainScreen mainScreen) {
        this.client = client;
        this.myUserId = myUserId;
        this.friend = friend;
        this.mainScreen = mainScreen;
        this.friendController = new CFriendController(client);
        this.cChatroomController = new CChatroomController(client);
        this.cFriendController = new CFriendController(client);

        setTitle(friend.getName() + "님의 프로필");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 상단 앱명 표시
        JLabel appTitle = new JLabel("일톡스", SwingConstants.CENTER);
        appTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(appTitle, BorderLayout.NORTH);

        // 프로필 정보 패널
        JPanel profilePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // 이름 표시
        gbc.gridx = 0;
        gbc.gridy = 0;
        profilePanel.add(new JLabel("이름:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel(friend.getName());
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        profilePanel.add(nameLabel, gbc);

        // ID 표시
        gbc.gridx = 0;
        gbc.gridy = 1;
        profilePanel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel idLabel = new JLabel(friend.getUserId());
        idLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        profilePanel.add(idLabel, gbc);

        // 업무상태 표시
        gbc.gridx = 0;
        gbc.gridy = 2;
        profilePanel.add(new JLabel("업무상태:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        String workStatus = friend.getWorkStatus();
        JLabel statusLabel = new JLabel(
            (workStatus != null && !workStatus.isEmpty()) ? workStatus : "상태 메시지 없음"
        );
        statusLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        profilePanel.add(statusLabel, gbc);

        mainPanel.add(profilePanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton chatButton = new JButton("1:1 채팅");
        JButton deleteButton = new JButton("삭제");
        JButton blockButton = new JButton("차단");

        buttonPanel.add(chatButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(blockButton);

        chatButton.addActionListener(e -> {
            System.out.println("button click///////////");
            onPrivateChatButtonClick();
        });

        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                this,
                friend.getName() + "님을 친구 목록에서 삭제하시겠습니까?",
                "친구 삭제",
                JOptionPane.YES_NO_OPTION
            );
            
            if (result == JOptionPane.YES_OPTION) {
                friendController.deleteFriend(myUserId, friend.getUserId(), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                    @Override
                    protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                        if (data.getData() != null && data.getData()) {
                            JOptionPane.showMessageDialog(
                                FriendProfileScreen.this,
                                "친구가 삭제되었습니다.",
                                "삭제 완료",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            mainScreen.refreshFriendList(); // 친구 목록 새로고침
                            dispose(); // 프로필 화면 닫기
                        } else {
                            JOptionPane.showMessageDialog(
                                FriendProfileScreen.this,
                                "친구 삭제에 실패했습니다.",
                                "오류",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                });
            }
        });

        blockButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                this,
                friend.getName() + "님을 차단하시겠습니까?",
                "친구 차단",
                JOptionPane.YES_NO_OPTION
            );
            
            if (result == JOptionPane.YES_OPTION) {
                friendController.blockFriend(myUserId, friend.getUserId(), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                    @Override
                    protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                        if (data.getData() != null && data.getData()) {
                            JOptionPane.showMessageDialog(
                                FriendProfileScreen.this,
                                "친구가 차단되었습니다.",
                                "차단 완료",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            mainScreen.refreshFriendList(); // 친구 목록 새로고침
                            dispose(); // 프로필 화면 닫기
                        } else {
                            JOptionPane.showMessageDialog(
                                FriendProfileScreen.this,
                                "친구 차단에 실패했습니다.",
                                "오류",
                                JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                });
            }
        });

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void onPrivateChatButtonClick() {
        cFriendController.checkBlocked(myUserId, friend.getUserId(), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
            @Override
            protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                if (data.getData() != null && data.getData()) {
                    JOptionPane.showMessageDialog(FriendProfileScreen.this, "차단된 친구입니다.", "알림", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // 차단이 아니면 기존 1:1 채팅방 로직 실행
                cChatroomController.requestPrivateChatroom(myUserId, friend.getUserId(), new ClientInteractResponseSwing<SPrivateChatRoomResponsePacket>() {
                    @Override
                    protected void execute(SPrivateChatRoomResponsePacket data) {
                        Chatroom chatroom = data.getChatroom();
                        if (chatroom == null) {
                            JOptionPane.showMessageDialog(FriendProfileScreen.this, "1:1 채팅방 생성에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        mainScreen.addPrivateChatroom(chatroom);
                        mainScreen.markChatRoomOpen(chatroom.getChatroomId());

                        PrivateChatScreen privateChatScreen = new PrivateChatScreen(client, chatroom);
                        privateChatScreen.setVisible(true);
                        privateChatScreen.initiate();
                        FriendProfileScreen.this.dispose();

                        privateChatScreen.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                mainScreen.markChatRoomClosed(chatroom.getChatroomId());
                            }
                        });
                    }
                });
            }
        });
    }
} 