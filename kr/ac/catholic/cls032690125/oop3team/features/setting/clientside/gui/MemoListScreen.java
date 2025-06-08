package kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.memo.clientside.CMemoController;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.SMemoListResponsePacket;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.SMemoUpdateResponsePacket;
import kr.ac.catholic.cls032690125.oop3team.models.ChatMemo;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MemoListScreen extends JFrame {
    private List<ChatMemo> memos = new ArrayList<>();
    private JPanel memoListPanel;
    private Client client;
    private String userId;

    public MemoListScreen(JFrame parent, Client client, String userId) {
        setTitle("메모 목록");
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.client = client;
        this.userId = userId;

        CMemoController memoController = new CMemoController(client);
        memoController.requestMemoList(userId, new ClientInteractResponseSwing<SMemoListResponsePacket>() {
            @Override
            protected void execute(SMemoListResponsePacket data) {
                List<ChatMemo> memos = data.getMemos();
                updateMemoList(memos);
            }
        });

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

    private void updateMemoList(List<ChatMemo> memos) {
        this.memos = memos;
        updateMemoList();
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
            
            JLabel dateLabel = new JLabel("메모 날짜: " + memo.getTimestamp());
            dateLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
            
            JLabel contentLabel = new JLabel("메모 내용: " + memo.getChatContent());
            contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            
            infoPanel.add(dateLabel);
            infoPanel.add(contentLabel);

            

            // 버튼 패널
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton viewButton = new JButton("상세보기");
            viewButton.addActionListener(e -> showMemoDetail(memo));
            buttonPanel.add(viewButton);

            memoCard.add(infoPanel, BorderLayout.CENTER);
            // memoCard.add(memoPanel, BorderLayout.SOUTH); // 목록에서는 주석 처리
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

        // 날짜, 대화내용 라벨
        JLabel dateLabel = new JLabel("메모 날짜: " + memo.getTimestamp());
        JLabel contentLabel = new JLabel("메모 내용: " + memo.getChatContent());

        // 메모 상세만 편집 가능
        JTextArea memoArea = new JTextArea(memo.getMemo());
        memoArea.setLineWrap(true);
        memoArea.setWrapStyleWord(true);
        memoArea.setEditable(false);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        infoPanel.add(dateLabel);
        infoPanel.add(contentLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(memoArea), BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("수정");
        JButton saveButton = new JButton("저장");
        JButton cancelButton = new JButton("취소");
        JButton deleteButton = new JButton("삭제");
        JButton closeButton = new JButton("닫기");

        editButton.addActionListener(e -> {
            memoArea.setEditable(true);
            buttonPanel.removeAll();
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();
        });

        saveButton.addActionListener(e -> {
            String newMemo = memoArea.getText().trim();
            if (!newMemo.isEmpty()) {
                CMemoController memoController = new CMemoController(client);
                memoController.updateMemo(
                    userId,
                    memo.getTimestamp(),
                    newMemo,
                    new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                        @Override
                        protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                            if (data.getData()) {
                                memo.setMemo(newMemo);
                                JOptionPane.showMessageDialog(detailDialog, "메모가 수정되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                                updateMemoList();
                                detailDialog.dispose();
                            } else {
                                JOptionPane.showMessageDialog(detailDialog, "메모 수정 실패", "오류", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                );
            } else {
                JOptionPane.showMessageDialog(detailDialog, "메모를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            memoArea.setText(memo.getMemo());
            memoArea.setEditable(false);
            buttonPanel.removeAll();
            buttonPanel.add(editButton);
            buttonPanel.add(deleteButton);
            buttonPanel.add(closeButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();
        });

        deleteButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(detailDialog,
                "정말로 메모를 삭제하시겠습니까?",
                "메모 삭제",
                JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                CMemoController memoController = new CMemoController(client);
                memoController.deleteMemo(
                    userId,
                    memo.getTimestamp(),
                    new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                        @Override
                        protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                            if (data.getData()) {
                                memos.remove(memo);
                                updateMemoList();
                                JOptionPane.showMessageDialog(detailDialog, "메모가 삭제되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                                detailDialog.dispose();
                            } else {
                                JOptionPane.showMessageDialog(detailDialog, "메모 삭제 실패", "오류", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                );
            }
        });

        closeButton.addActionListener(e -> detailDialog.dispose());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        detailDialog.add(mainPanel);
        detailDialog.setVisible(true);
    }
} 