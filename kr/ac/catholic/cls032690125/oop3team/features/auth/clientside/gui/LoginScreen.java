package kr.ac.catholic.cls032690125.oop3team.features.auth.clientside.gui;

import javax.swing.*;
import java.awt.*;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.auth.clientside.CAuthController;
import kr.ac.catholic.cls032690125.oop3team.features.auth.shared.SLoginResponse;
import kr.ac.catholic.cls032690125.oop3team.client.MainScreen;
import kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.CFriendController;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;

public class LoginScreen extends JFrame {
    private Client client;
    private CAuthController authController;

    public LoginScreen(Client client) {
        this.client = client;
        this.authController = new CAuthController(client);
        setTitle("로그인");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // 상단 타이틀
        JLabel titleLabel = new JLabel("일톡스", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 입력 폼
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 아이디
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("아이디:"), gbc);
        gbc.gridx = 1;
        JTextField idField = new JTextField(15);
        formPanel.add(idField, gbc);

        // 비밀번호
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("비밀번호:"), gbc);
        gbc.gridx = 1;
        JPasswordField passField = new JPasswordField(15);
        formPanel.add(passField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // 버튼 패널 (가로 배치)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton loginBtn = new JButton("로그인");
        JButton signupBtn = new JButton("회원가입");
        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 로그인 버튼
        loginBtn.addActionListener(e -> {
            String userId = idField.getText();
            String pass = new String(passField.getPassword());
            authController.sendLogin(userId, pass, new ClientInteractResponseSwing<>(){
                @Override
                protected void execute(SLoginResponse data) {
                    if(data.isSuccess()) {
                        JOptionPane.showMessageDialog(LoginScreen.this, "로그인 성공!");
                        
                        // 친구목록 조회
                        CFriendController friendController = new CFriendController(client);
                        friendController.getFriendList(userId, new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
                            @Override
                            protected void execute(ServerResponsePacketSimplefied<UserProfile[]> data) {
                                if (data.getData() != null) {
                                    System.out.println("=== 친구목록 조회 결과 ===");
                                    for (UserProfile friend : data.getData()) {
                                        System.out.println("친구 ID: " + friend.getUserId() + ", 이름: " + friend.getName());
                                    }
                                    System.out.println("=====================");
                                } else {
                                    System.out.println("친구목록 조회 실패 또는 친구가 없습니다.");
                                }
                            }
                        });
                        
                        new MainScreen(userId, client).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginScreen.this, "로그인 실패!");
                    }
                }
            });
        });

        // 회원가입 버튼
        signupBtn.addActionListener(e -> {
            new SignupScreen(client).setVisible(true);
            dispose();
        });
    }
} 