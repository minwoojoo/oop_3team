package gui.friend;

import javax.swing.*;
import java.awt.*;

public class ChatWindowUi extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ChatWindowUi(String friendName) {
        setTitle("1:1 채팅 - " + friendName);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        sendButton = new JButton("전송");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Getters for controller to add listeners and update UI

    public JTextArea getChatArea() {
        return chatArea;
    }

    public JTextField getMessageField() {
        return messageField;
    }

    public JButton getSendButton() {
        return sendButton;
    }
}

