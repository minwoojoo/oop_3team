package kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.gui;

import javax.swing.*;
import java.awt.*;

public class FriendProfileScreen extends JFrame {
    public FriendProfileScreen(String friendName, String statusMessage) {
        setTitle("친구 프로필");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        JLabel nameLabel = new JLabel(friendName);
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        profilePanel.add(nameLabel, gbc);

        // 상태 메시지 표시
        gbc.gridx = 0;
        gbc.gridy = 1;
        profilePanel.add(new JLabel("상태 메시지:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JLabel statusLabel = new JLabel(statusMessage);
        statusLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        profilePanel.add(statusLabel, gbc);

        mainPanel.add(profilePanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton chatButton = new JButton("1:1 채팅");
        JButton deleteButton = new JButton("🗑️ 삭제");
        JButton blockButton = new JButton("🚫 차단");

        buttonPanel.add(chatButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(blockButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 버튼 이벤트 처리
        chatButton.addActionListener(e -> {
            // 채팅 화면으로 이동
            //PrivateChatScreen chatScreen = new PrivateChatScreen(friendName);
            //chatScreen.setVisible(true);
            //dispose();
        });

        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "정말로 " + friendName + "님을 삭제하시겠습니까?", 
                "친구 삭제", 
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        blockButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "정말로 " + friendName + "님을 차단하시겠습니까?", 
                "친구 차단", 
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        add(mainPanel);
    }
} 