package gui.friend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatScreen extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ChatScreen(String friendName) {
        setTitle("1:1 채팅 - " + friendName);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel appTitle = new JLabel("일톡스", SwingConstants.CENTER);
        appTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(appTitle, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        sendButton = new JButton("전송");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public String getMessageText() {
        return messageField.getText().trim();
    }

    public void clearMessageField() {
        messageField.setText("");
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    public void addSendMessageListener(ActionListener listener) {
        sendButton.addActionListener(listener);
        messageField.addActionListener(listener);
    }
}
