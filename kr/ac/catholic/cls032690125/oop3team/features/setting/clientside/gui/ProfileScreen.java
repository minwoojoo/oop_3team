package kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.CUpdateUserProfileRequest;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.SUpdateUserProfileResponse;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.CSettingController;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.SWorkStatusUpdateRes;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.SWorkStatusGetRes;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.SUserNameGetRes;


public class ProfileScreen extends JFrame {
    private JTextField nameField;
    private JTextArea statusArea;
    private JLabel profileImageLabel;
    private String[] profileImages = {
        "일톡스"
    };
    private final Client client;
    private final String userId;
    private CSettingController settingController;

    public ProfileScreen(JFrame parent, Client client, String userId) {
        super("프로필 설정");
        this.client = client;
        this.userId = userId;
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
        nameField = new JTextField();
        nameField.setEditable(false); // 이름은 수정 불가능하도록 설정
        
        JLabel statusLabel = new JLabel("상태 메시지:");
        statusArea = new JTextArea();
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

            System.out.println("업무상태 변경 요청: " + status);
            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "이름을 입력해주세요.",
                        "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 업무상태 변경 요청
            settingController.updateWorkStatus(userId, status, new ClientInteractResponseSwing<SWorkStatusUpdateRes>() {
                @Override
                protected void execute(SWorkStatusUpdateRes res) {
                    if (res.isSuccess()) {
                        JOptionPane.showMessageDialog(ProfileScreen.this, "업무 상태가 변경되었습니다.", "저장 완료", JOptionPane.INFORMATION_MESSAGE);
                        // 필요하다면 메인화면 등 다른 화면도 갱신
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(ProfileScreen.this, "업무 상태 변경에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            // 프로필 저장 처리
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

        // 컨트롤러 초기화
        this.settingController = new CSettingController(client);

        // 사용자 이름 조회
        settingController.getUserName(userId, res -> {
            String name = res.getData();
            if (name != null) {
                nameField.setText(name);
            } else {
                nameField.setText("이름을 불러올 수 없습니다.");
            }
        });

        // 상태 메시지(work_status) 서버에서 조회해서 표시
        settingController.getWorkStatus(userId, new ClientInteractResponseSwing<SWorkStatusGetRes>() {
            @Override
            protected void execute(SWorkStatusGetRes res) {
                if (res.isSuccess()) {
                    statusArea.setText(res.getWorkStatus());
                } else {
                    statusArea.setText("상태 메시지 없음");
                }
            }
        });
    }
} 