package gui.auth;

import javax.swing.*;
import java.awt.*;

public class SignupScreen extends JFrame {
    public SignupScreen() {
        setTitle("회원가입");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // 이름 입력
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("이름:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);

        // 아이디 입력
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("아이디:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField idField = new JTextField(15);
        panel.add(idField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        JButton checkIdButton = new JButton("중복확인");
        panel.add(checkIdButton, gbc);

        // 비밀번호 입력
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("비밀번호:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // 비밀번호 확인
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("비밀번호 확인:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JPasswordField confirmPasswordField = new JPasswordField(15);
        panel.add(confirmPasswordField, gbc);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton signupButton = new JButton("가입하기");
        JButton cancelButton = new JButton("취소");

        buttonPanel.add(signupButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(buttonPanel, gbc);

        // 버튼 이벤트 처리
        signupButton.addActionListener(e -> {
            // 회원가입 처리 후 로그인 화면으로 이동
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            // 로그인 화면으로 돌아가기
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
            dispose();
        });

        add(panel);
    }
} 