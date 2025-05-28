package kr.ac.catholic.cls032690125.oop3team.client.gui.setting;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MemoListScreen extends JFrame {
    private List<ChatMemo> memos = new ArrayList<>();
    private JPanel memoListPanel;

    public MemoListScreen(JFrame parent) {
        setTitle("메모 목록");
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 샘플 데이터 추가
        memos.add(new ChatMemo("2025.04.29 14:23", 
            "오늘 회의 오후 3시로 변경되었습니다", 
            "회의 준비 자료도 함께 전달하기"));
        memos.add(new ChatMemo("2025.04.28 10:15", 
            "프로젝트 마감일이 다음 주로 연기되었습니다", 
            "팀원들에게 연락하기"));
        memos.add(new ChatMemo("2025.04.27 16:45", 
            "클라이언트 미팅 일정 확인", 
            "발표 자료 준비 필요"));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 메모 목록 패널
        memoListPanel = new JPanel();
        memoListPanel.setLayout(new BoxLayout(memoListPanel, BoxLayout.Y_AXIS));
        updateMemoList();

        JScrollPane scrollPane = new JScrollPane(memoListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void updateMemoList() {
        memoListPanel.removeAll();
        
        for (ChatMemo memo : memos) {
            JPanel memoCard = new JPanel(new BorderLayout(5, 5));
            memoCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            memoCard.setBackground(Color.WHITE);
            memoCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            // 메모 정보 패널
            JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            
            JLabel dateLabel = new JLabel("📅 " + memo.getTimestamp());
            dateLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
            
            JLabel contentLabel = new JLabel("💬 " + memo.getChatContent());
            contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            
            infoPanel.add(dateLabel);
            infoPanel.add(contentLabel);

            // 메모 요약 패널
            JPanel memoPanel = new JPanel(new BorderLayout());
            JLabel memoLabel = new JLabel("📝 " + memo.getMemo());
            memoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            memoPanel.add(memoLabel, BorderLayout.WEST);

            // 버튼 패널
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton viewButton = new JButton("상세보기");
            JButton deleteButton = new JButton("삭제");

            viewButton.addActionListener(e -> showMemoDetail(memo));
            deleteButton.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(this,
                    "정말로 메모를 삭제하시겠습니까?",
                    "메모 삭제",
                    JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                    memos.remove(memo);
                    updateMemoList();
                }
            });

            buttonPanel.add(viewButton);
            buttonPanel.add(deleteButton);

            memoCard.add(infoPanel, BorderLayout.CENTER);
            memoCard.add(memoPanel, BorderLayout.SOUTH);
            memoCard.add(buttonPanel, BorderLayout.EAST);
            
            memoListPanel.add(memoCard);
            memoListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        memoListPanel.revalidate();
        memoListPanel.repaint();
    }

    private void showMemoDetail(ChatMemo memo) {
        JDialog detailDialog = new JDialog(this, "메모 상세보기", true);
        detailDialog.setSize(400, 300);
        detailDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 메모 내용 표시
        JTextArea contentArea = new JTextArea();
        contentArea.setText("📅 " + memo.getTimestamp() + "\n\n" +
                          "💬 " + memo.getChatContent() + "\n\n" +
                          "📝 " + memo.getMemo());
        contentArea.setEditable(false);
        contentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        contentArea.setBackground(new Color(240, 240, 240));

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");
        JButton closeButton = new JButton("닫기");

        editButton.addActionListener(e -> {
            // 편집 모드로 전환
            contentArea.setEditable(true);
            contentArea.setBackground(Color.WHITE);
            
            // 버튼 패널 변경
            buttonPanel.removeAll();
            JButton saveButton = new JButton("저장");
            JButton cancelButton = new JButton("취소");
            
            saveButton.addActionListener(saveEvent -> {
                String[] lines = contentArea.getText().split("\n");
                if (lines.length >= 6) { // 최소한의 형식 확인
                    String newMemo = lines[5].substring(2).trim(); // "📝 " 제거
                    if (!newMemo.isEmpty()) {
                        memo.setMemo(newMemo);
                        JOptionPane.showMessageDialog(detailDialog,
                            "메모가 수정되었습니다.",
                            "알림",
                            JOptionPane.INFORMATION_MESSAGE);
                        updateMemoList();
                        detailDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(detailDialog,
                            "메모를 입력해주세요.",
                            "입력 오류",
                            JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            
            cancelButton.addActionListener(cancelEvent -> {
                contentArea.setText("📅 " + memo.getTimestamp() + "\n\n" +
                                  "💬 " + memo.getChatContent() + "\n\n" +
                                  "📝 " + memo.getMemo());
                contentArea.setEditable(false);
                contentArea.setBackground(new Color(240, 240, 240));
                buttonPanel.removeAll();
                buttonPanel.add(editButton);
                buttonPanel.add(deleteButton);
                buttonPanel.add(closeButton);
                buttonPanel.revalidate();
                buttonPanel.repaint();
            });
            
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();
        });

        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(detailDialog,
                "정말로 메모를 삭제하시겠습니까?",
                "메모 삭제",
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                memos.remove(memo);
                updateMemoList();
                detailDialog.dispose();
            }
        });

        closeButton.addActionListener(e -> detailDialog.dispose());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        mainPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        detailDialog.add(mainPanel);
        detailDialog.setVisible(true);
    }

    public static class ChatMemo {
        private String timestamp;
        private String chatContent;
        private String memo;

        public ChatMemo(String timestamp, String chatContent, String memo) {
            this.timestamp = timestamp;
            this.chatContent = chatContent;
            this.memo = memo;
        }

        public String getTimestamp() { return timestamp; }
        public String getChatContent() { return chatContent; }
        public String getMemo() { return memo; }
        public void setMemo(String memo) { this.memo = memo; }
    }
} 