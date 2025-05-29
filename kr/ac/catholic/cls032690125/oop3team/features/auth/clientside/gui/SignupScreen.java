package kr.ac.catholic.cls032690125.oop3team.features.auth.clientside.gui;

import javax.swing.*;
import java.awt.*;
import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.auth.clientside.CAuthController;
import kr.ac.catholic.cls032690125.oop3team.models.responses.SignupResult;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

public class SignupScreen extends JFrame {
    private Client client;
    private boolean isIdChecked = false;
    private CAuthController authController;

    public SignupScreen(Client client) {
        this.client = client;
        authController = new CAuthController(client);
        setTitle("회원가입");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // 이름
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("이름:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);

        // 아이디
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("아이디:"), gbc);
        gbc.gridx = 1;
        JTextField idField = new JTextField(15);
        panel.add(idField, gbc);
//        gbc.gridx = 2;
//        JButton checkIdBtn = new JButton("중복확인");
//        panel.add(checkIdBtn, gbc);

        // 비밀번호
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("비밀번호:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // 비밀번호 확인
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("비밀번호 확인:"), gbc);
        gbc.gridx = 1;
        JPasswordField confirmPasswordField = new JPasswordField(15);
        panel.add(confirmPasswordField, gbc);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton signupButton = new JButton("가입하기");
        JButton cancelButton = new JButton("취소");
        buttonPanel.add(signupButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        panel.add(buttonPanel, gbc);

        add(panel);

        // 중복확인 버튼
//        checkIdBtn.addActionListener(e -> {
//            String id = idField.getText();
//            if (id.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
//                return;
//            }
//
//            try {
//                if (false){//client.isIdDuplicate(id)) {
//                    JOptionPane.showMessageDialog(this, "이미 사용 중인 아이디입니다.");
//                    isIdChecked = false;
//                } else {
//                    JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.");
//                    isIdChecked = true;
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(this, "서버 연결 오류!");
//            }
//        });

        // 가입하기 버튼
        signupButton.addActionListener(e -> {
            String name = nameField.getText();
            String id = idField.getText();
            String pass = new String(passwordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());

//            if (!isIdChecked) {
//                JOptionPane.showMessageDialog(this, "아이디 중복확인을 해주세요.");
//                return;
//            }
            if (!pass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
                return;
            }
            authController.sendSignUp(id, pass, name, new ClientInteractResponseSwing<>() {
                @Override
                protected void execute(ServerResponsePacketSimplefied<SignupResult> data) {
                    switch(data.getData()) {
                        case SUCCESS:
                            JOptionPane.showMessageDialog(SignupScreen.this, "회원가입 성공! 로그인 해주세요.");
                            new LoginScreen(client).setVisible(true);
                            dispose();
                            break;
                        case FAILED:
                            JOptionPane.showMessageDialog(SignupScreen.this, "회원가입 실패!");
                            break;
                        case DUPLICATED:
                            JOptionPane.showMessageDialog(SignupScreen.this, "이미 사용 중인 아이디입니다.");
                            break;
                    }
                }
            });
        });

        // 취소 버튼
        cancelButton.addActionListener(e -> {
            new LoginScreen(client).setVisible(true);
            dispose();
        });
    }
}