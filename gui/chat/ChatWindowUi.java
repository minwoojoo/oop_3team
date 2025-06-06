package gui.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindowUi extends JFrame {
    private String myName;
    private String targetName;
    private ChatController controller;

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    public ChatWindowUi(String myName, String targetName, ChatController controller) {
        this.myName = myName;
        this.targetName = targetName;
        this.controller = controller;

        setTitle("채팅창 - " + targetName);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("전송");

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = messageField.getText().trim();
                if (!text.isEmpty()) {
                    controller.sendMessage(myName, targetName, text);
                    messageField.setText("");
                }
            }
        });
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }
}
