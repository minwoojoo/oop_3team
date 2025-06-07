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
import java.util.Arrays;
import java.util.List;

public class ThreadChatScreen extends JFrame implements ChatScreenBase {
    private JTextArea chatArea;
    private JTextField messageField;
    private List<Message> messages;
    private Client client;
    private CChatroomIndividualController controller;
    private Chatroom parentChatroom;
    private Chatroom threadChatroom;

    public ThreadChatScreen(Client client, Chatroom threadChatroom, Chatroom parentChatroom) {
        this.client = client;
        this.threadChatroom = threadChatroom;
        this.parentChatroom = parentChatroom;
        this.controller = new CChatroomIndividualController(client, threadChatroom, this);

        setTitle("스레드: " + threadChatroom.getTitle());
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        messages = new ArrayList<>();

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 상단 패널: 상위 채팅방 이름 + 스레드 제목
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 상위 채팅방 이름
        JLabel parentTitleLabel = new JLabel("상위 채팅방: " + parentChatroom.getTitle(), SwingConstants.LEFT);
        parentTitleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        parentTitleLabel.setForeground(new Color(100, 100, 100));
        
        // 스레드 제목
        JLabel threadTitleLabel = new JLabel(threadChatroom.getTitle(), SwingConstants.LEFT);
        threadTitleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        threadTitleLabel.setForeground(Color.BLACK);
        
        // 닫기 버튼
        JButton closeButton = new JButton("닫기");
        closeButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        closeButton.addActionListener(e -> {/* 아무 동작도 하지 않음 */});
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topRightPanel.setOpaque(false);
        topRightPanel.add(closeButton);
        
        // 제목 패널
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(parentTitleLabel);
        titlePanel.add(threadTitleLabel);
        topPanel.add(titlePanel, BorderLayout.CENTER);
        topPanel.add(topRightPanel, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);

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
        sendButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
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
                        messageField.setText("오류가 발생했습니다");
                }
            });
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible) initiate();
    }

    @Override
    public void initiate() {
        client.getChatReceiver().registerChatroom(controller);
        System.out.println("ThreadChatScreen initiate");
        controller.initiateMessage(1000000, new ClientInteractResponseSwing<SMessageLoadPacket>() {
            @Override
            protected void execute(SMessageLoadPacket data) {
                StringBuilder str = new StringBuilder();
                var msgs = Arrays.asList(data.getMessages());
                System.out.println(data.getMessages().length);
                for(Message message : msgs) {
                    System.out.println(message.getContent());
                    str.append("["+message.getSenderId()+"] "+message.getContent()).append("\n");
                }
                msgs.addAll(messages);
                messages = msgs;
                str.append(chatArea.getText());
                chatArea.setText(str.toString());
            }
        });
    }

    @Override
    public void onChatMessage(Message message) {
        addMessage(message);
    }

    private void addMessage(Message message) {
        StringBuilder str = new StringBuilder(chatArea.getText());
        str.append("["+message.getSenderId()+"] "+message.getContent()).append("\n");
        chatArea.setText(str.toString());
    }
}
