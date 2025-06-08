package kr.ac.catholic.cls032690125.oop3team.features.thread.clientside.gui.dialog;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.GroupChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.ThreadChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.CChatroomCreatePacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomCreatePacket;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ThreadCreateDialog extends JDialog {
    private Client client;
    private GroupChatScreen screen;
    private CChatroomIndividualController controller;
    private CChatroomController cChatroomController;

    public ThreadCreateDialog(Client client, GroupChatScreen screen) {
        super(screen, "새 스레드 만들기", true);

        this.client = client;
        this.screen = screen;
        this.controller = screen.getController();
        this.cChatroomController = new CChatroomController(client);

        setSize(300, 150);
        setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("스레드 제목을 입력하세요");
        JTextField titleField = new JTextField();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton createButton = new JButton("생성");
        JButton cancelButton = new JButton("취소");

        createButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            if (!title.isEmpty()) {
                create(title);
            } else {
                JOptionPane.showMessageDialog(this,
                        "스레드 제목을 입력해주세요.",
                        "입력 오류",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(createButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(titleField, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void create(String title) {
        ArrayList<String> participants = new ArrayList<>(screen.getMembers());
        String ownerId = client.getCurrentSession().getUserId();
        Integer parentId = screen.getChatroom().getChatroomId();

        CChatroomCreatePacket cChatroomCreatePacket = new CChatroomCreatePacket(title, ownerId, participants, parentId, false);
        
        cChatroomController.sendCreateChatroom(cChatroomCreatePacket, new ClientInteractResponseSwing<SChatroomCreatePacket>() {
            @Override
            protected void execute(SChatroomCreatePacket data) {
                Chatroom newThreadRoom = data.getRoom();
                if (newThreadRoom != null) {
                    screen.addNewThread(newThreadRoom, title);
                    
                    // 약간의 지연 후 ThreadListDialog 열기
                    Timer timer = new Timer(500, e -> {
                        SwingUtilities.invokeLater(() -> {
                            new ThreadListDialog(
                                screen,
                                client,
                                screen.getChatroom(),
                                cChatroomController
                            ).setVisible(true);
                            dispose(); // 다이얼로그 닫기
                        });
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    // DB에는 저장되었지만 응답이 제대로 오지 않은 경우
                    // 스레드 목록 다이얼로그를 새로 열어서 목록 갱신
                    SwingUtilities.invokeLater(() -> {
                        new ThreadListDialog(
                            screen,
                            client,
                            screen.getChatroom(),
                            cChatroomController
                        ).setVisible(true);
                        dispose(); // 다이얼로그 닫기
                    });
                }
            }
        });
    }
}
