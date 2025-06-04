package gui.memo;

import javax.swing.*;
import java.awt.*;

public class ChatMemoPopup extends JDialog {
    private JTextArea memoArea;
    private String chatContent;
    private String timestamp;

    public ChatMemoPopup(JFrame parent, String chatContent, String timestamp) {
        super(parent, "메모 추가", true);
        this.chatContent = chatContent;
        this.timestamp = timestamp;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 채팅 내용 표시 패널
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBorder(BorderFactory.createTitledBorder("선택한 채팅"));
        
        JTextArea chatArea = new JTextArea();
        chatArea.setText("[" + timestamp + "]\n" + chatContent);
        chatArea.setEditable(false);
        chatArea.setBackground(new Color(240, 240, 240));
        chatArea.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // 메모 입력 패널
        JPanel memoPanel = new JPanel(new BorderLayout());
        memoPanel.setBorder(BorderFactory.createTitledBorder("추가 메모"));
        
        memoArea = new JTextArea();
        memoArea.setLineWrap(true);
        memoArea.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        memoPanel.add(new JScrollPane(memoArea), BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton confirmButton = new JButton("확인");
        JButton cancelButton = new JButton("취소");

        confirmButton.addActionListener(e -> {
            String memo = memoArea.getText().trim();
            if (!memo.isEmpty()) {
                // 메모 저장 로직 구현
                JOptionPane.showMessageDialog(this, "메모가 저장되었습니다.", 
                    "알림", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "메모를 입력해주세요.", 
                    "입력 오류", JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        mainPanel.add(chatPanel, BorderLayout.NORTH);
        mainPanel.add(memoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
} 