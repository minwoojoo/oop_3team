package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomMemberListPacket;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Friend;
import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PrivateChatScreen extends JFrame implements ChatScreenBase {
    private JTextArea chatArea;
    private JTextField messageField;
    private List<Message> messages;
    private List<String> members;
    private Map<String, String> userIdToName = new HashMap<>();

    private void addMessage(Message message) {
        StringBuilder str = new StringBuilder(chatArea.getText());
        String senderName = userIdToName.getOrDefault(message.getSenderId(), message.getSenderId());
        String timeStr = formatMessageTime(message.getSent());
        str.append("[" + senderName + "] " + message.getContent() + " (" + timeStr + ")").append("\n");
        chatArea.setText(str.toString());
    }

    private String formatMessageTime(java.time.LocalDateTime dateTime) {
        java.time.ZoneId zoneId = java.time.ZoneId.systemDefault();
        long timestamp = dateTime.atZone(zoneId).toInstant().toEpochMilli();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        return sdf.format(new java.util.Date(timestamp));
    }

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

    @Override
    public void setVisible(boolean visible) {
        if(visible) initiate();
        super.setVisible(visible);
    }

    @Override
    public void initiate() {
        client.getChatReceiver().registerChatroom(controller);
        fetchAndStoreMembers();
        System.out.println("PrivateChatScreen initiate");
        controller.initiateMessage(1000000, new ClientInteractResponseSwing<SMessageLoadPacket>() {
            @Override
            protected void execute(SMessageLoadPacket data) {
                messages = new ArrayList<>(Arrays.asList(data.getMessages()));
                StringBuilder str = new StringBuilder();
                for(Message message : messages) {
                    String senderName = userIdToName.getOrDefault(message.getSenderId(), message.getSenderId());
                    String timeStr = formatMessageTime(message.getSent());
                    str.append("[" + senderName + "] " + message.getContent() + " (" + timeStr + ")").append("\n");
                }
                chatArea.setText(str.toString());
            }
        });
    }

    private void fetchAndStoreMembers() {
        controller.getMemberList(new ClientInteractResponseSwing<SChatroomMemberListPacket>() {
            @Override
            protected void execute(SChatroomMemberListPacket data) {
                setMembers(data.getMembers());
                // id→name 매핑
                userIdToName.clear();
                for (int i = 0; i < data.getMembers().size(); i++) {
                    userIdToName.put(data.getMembers().get(i), data.getMemberNames().get(i));
                }
            }
        });
    }

    @Override
    public void onChatMessage(Message message) {
        if (!messages.contains(message)) {
            messages.add(message);
            addMessage(message);
        }
    }

    private void setMembers(List<String> members) {
        this.members = members;
    }
}