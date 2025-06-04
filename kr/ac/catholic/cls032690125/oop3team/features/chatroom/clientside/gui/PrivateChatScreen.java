package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PrivateChatScreen extends JFrame implements ChatScreenBase {
    private JTextArea chatArea;
    private JTextField messageField;
    private List<String> messages;

    private Client client;
    private CChatroomIndividualController controller;

    public PrivateChatScreen(Client client, Chatroom chatroom) {
        this.client = client;
        controller = new CChatroomIndividualController(client, chatroom, this);

        setTitle(chatroom.getTitle());
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        messages = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 상단 앱명 표시
        JLabel appTitle = new JLabel("일톡스", SwingConstants.CENTER);
        appTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(appTitle, BorderLayout.NORTH);

        // 채팅 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 메시지 입력 영역
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JButton sendButton = new JButton("전송");
        
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // 전송 버튼 이벤트 처리
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());

        add(mainPanel);
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            controller.sendMessage(message, new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                @Override
                protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                    if(data.getData())
                        messageField.setText("");
                    else
                        messageField.setText("오류가 발생했습니다"); //TODO do better
                }
            });

        }
    }

    private void updateChatArea() {
        StringBuilder chatText = new StringBuilder();
        for (String message : messages) {
            chatText.append(message).append("\n");
        }
        chatArea.setText(chatText.toString());
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    @Override
    public void initiate() {
        client.getChatReceiver().registerChatroom(controller);
        controller.initiateMessage(1000000, new ClientInteractResponseSwing<SMessageLoadPacket>() {
            @Override
            protected void execute(SMessageLoadPacket data) {
                //data에 이전 채팅 정보 담김
            }
        });
        //TODO: 이전 채팅 불러오기, 맴버 불러오기 등
    }

    @Override
    public void onChatMessage(Message message) {
        // TODO: 이거는ㅃ 뿌리면 ㄷㅁ
    }
}