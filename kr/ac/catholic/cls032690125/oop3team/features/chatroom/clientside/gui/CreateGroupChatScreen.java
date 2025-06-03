package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.MainScreen;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.server.Server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreateGroupChatScreen extends JFrame {
    private List<JCheckBox> friendCheckBoxes;
    private List<String> friendNames;

    private Client client;
    private Server server;
    private Chatroom chatroom;

    public CreateGroupChatScreen() {
        setTitle("그룹 대화방 생성");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // MainScreen의 친구 이름 리스트 사용
        friendNames = MainScreen.friendNames;
        friendCheckBoxes = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 상단 앱명 표시
        JLabel appTitle = new JLabel("일톡스", SwingConstants.CENTER);
        appTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(appTitle, BorderLayout.NORTH);

        // 친구 선택 영역
        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
        
        for (String name : friendNames) {
            JCheckBox checkBox = new JCheckBox(name);
            checkBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            friendCheckBoxes.add(checkBox);
            friendListPanel.add(checkBox);
        }

        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼 영역
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton nextButton = new JButton("다음");
        buttonPanel.add(nextButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 다음 버튼 이벤트 처리
        nextButton.addActionListener(e -> {
            int selectedCount = 0;
            for (JCheckBox checkBox : friendCheckBoxes) {
                if (checkBox.isSelected()) {
                    selectedCount++;
                }
            }

            if (selectedCount < 2) {
                JOptionPane.showMessageDialog(this, 
                    "2명 이상의 친구를 선택해주세요.", 
                    "알림", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                // 대화방 이름 입력 팝업
                String groupName = JOptionPane.showInputDialog(this, 
                    "대화방 이름을 입력하세요:", 
                    "대화방 이름 입력", 
                    JOptionPane.PLAIN_MESSAGE);

                if (groupName != null && !groupName.trim().isEmpty()) {
                    // 채팅방 생성 및 입장
                    GroupChatScreen groupChatScreen = new GroupChatScreen(groupName, getSelectedFriends(),client,server,chatroom);
                    groupChatScreen.setVisible(true);
                    dispose();
                }
            }
        });

        add(mainPanel);
    }

    private List<String> getSelectedFriends() {
        List<String> selectedFriends = new ArrayList<>();
        for (int i = 0; i < friendCheckBoxes.size(); i++) {
            if (friendCheckBoxes.get(i).isSelected()) {
                selectedFriends.add(friendNames.get(i));
            }
        }
        return selectedFriends;
    }
} 