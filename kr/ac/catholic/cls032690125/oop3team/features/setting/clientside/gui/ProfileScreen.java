package kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.CUpdateUserProfileRequest;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.SUpdateUserProfileResponse;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ProfileScreen extends JFrame {
    private JTextField nameField;
    private JTextArea statusArea;
    private JLabel profileImageLabel;
    private String[] profileImages = {
            "일톡스"
    };

    public ProfileScreen(JFrame parent, Client client) {
        setTitle("프로필 설정");
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 프로필 이미지 패널
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        profileImageLabel = new JLabel(profileImages[new Random().nextInt(profileImages.length)]);
        profileImageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 50));
        imagePanel.add(profileImageLabel);
        mainPanel.add(imagePanel, BorderLayout.NORTH);

        // 정보 입력 패널
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        JLabel nameLabel = new JLabel("이름:");
        nameField = new JTextField("홍길동");

        JLabel statusLabel = new JLabel("상태 메시지:");
        statusArea = new JTextArea("오늘도 행복한 하루!");
        statusArea.setLineWrap(true);
        JScrollPane statusScroll = new JScrollPane(statusArea);

        infoPanel.add(nameLabel);
        infoPanel.add(nameField);
        infoPanel.add(statusLabel);
        infoPanel.add(statusScroll);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("저장");
        JButton cancelButton = new JButton("취소");

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String status = statusArea.getText();

            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "이름을 입력해주세요.",
                        "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 프로필 저장 처리
            String userId = client.getCurrentSession().getUserId();
            var packet = new CUpdateUserProfileRequest(userId, name, status);
            client.request(packet, response -> {
                if (response instanceof SUpdateUserProfileResponse res){
                    if (res.isSuccess()){
                        JOptionPane.showMessageDialog(this,"프로필이 저장되었습니다",
                                "성공",JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(this,res.getMessage(),
                                "실패",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
} 