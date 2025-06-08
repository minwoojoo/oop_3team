package kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.keyword.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Keyword;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KeywordSettingsScreen extends JFrame {
    private JPanel keywordPanel;
    private JButton saveButton;
    private JTextField keywordField;
    private Client client;
    private Chatroom chatroom;
    private List<String> keywords = new ArrayList<>();


    public KeywordSettingsScreen(JFrame parent, Client client, Chatroom chatroom) {
        this.client = client;
        this.chatroom = chatroom;

        setTitle("우선 알림 키워드 설정");
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 상단 설명 텍스트
        JLabel descriptionLabel = new JLabel("중요 키워드를 등록해 우선 알림을 받아보세요");
        descriptionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        mainPanel.add(descriptionLabel, BorderLayout.NORTH);

        // 중앙 패널 (키워드 입력과 목록을 포함)
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));

        // 키워드 입력 영역
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        keywordField = new JTextField();
        JButton addButton = new JButton("추가");
        addButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        addButton.setPreferredSize(new Dimension(60, 30));
        addButton.addActionListener(e -> addKeywordToServer());

        inputPanel.add(keywordField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        centerPanel.add(inputPanel, BorderLayout.NORTH);

        // 등록된 키워드 목록
        keywordPanel = new JPanel();
        keywordPanel.setLayout(new BoxLayout(keywordPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(keywordPanel);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // 추천 키워드 패널
        JPanel recommendedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        recommendedPanel.setBorder(BorderFactory.createTitledBorder("추천 키워드"));
        String[] recommendedKeywords = {"마감", "회의", "보고", "긴급", "중요"};
        for (String keyword : recommendedKeywords) {
            JButton keywordButton = new JButton(keyword);
            keywordButton.addActionListener(e -> {
                keywordField.setText(keyword);
                addKeywordToServer();
            });
            recommendedPanel.add(keywordButton);
        }
        centerPanel.add(recommendedPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

         //저장 버튼
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        saveButton = new JButton("저장");
//        saveButton.setEnabled(false);
//        saveButton.addActionListener(e -> {
//            // 키워드 저장 로직 구현
//            dispose();
//        });
//        bottomPanel.add(saveButton);
//        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        fetchKeywordsFromServer();
    }

    private void fetchKeywordsFromServer() {
        String userId = client.getCurrentSession().getUserId();
        int chatroomId = chatroom.getChatroomId();
        CGetKeywordListRequest request = new CGetKeywordListRequest(userId, chatroomId);
        client.request(request, packet -> {
            if (packet instanceof SGetKeywordListResponse res) {
                System.out.println("[DEBUG] Keyword list response received.");
                SwingUtilities.invokeLater(() -> {
                    keywords = new ArrayList<>();
                    System.out.println("[DEBUG] Keywords from server:");
                    for (Keyword k : res.getKeywords()) {
                        System.out.println(" - " + k.getKeyword());
                        keywords.add(k.getKeyword());
                    }
                    updateKeywordList();
                });
            } else {
                System.out.println("[DEBUG] Unexpected packet type: " + packet.getClass().getSimpleName());
            }
        });


    }

    private void addKeywordToServer() {
        String keyword = keywordField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "키워드를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (keywords.contains(keyword)) {
            JOptionPane.showMessageDialog(this, "이미 등록된 키워드입니다.", "중복 키워드", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String userId = client.getCurrentSession().getUserId();
        int chatroomId = chatroom.getChatroomId();
        CAddKeywordRequest request = new CAddKeywordRequest(userId, chatroomId, keyword);

        client.request(request, packet -> {
            if (packet instanceof SAddKeywordResponse res) {
                SwingUtilities.invokeLater(() -> {
                    if (res.isSuccess()) {
                        keywords.add(keyword);
                        updateKeywordList();
                        keywordField.setText("");
                        JOptionPane.showMessageDialog(this, "키워드가 성공적으로 추가되었습니다!", "성공", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, res.getMessage(), "추가 실패", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        });
    }


    private void deleteKeywordFromServer(String keyword) {
        String userId = client.getCurrentSession().getUserId();
        int chatroomId = chatroom.getChatroomId();
        CDeleteKeywordRequest request = new CDeleteKeywordRequest(userId, chatroomId, keyword);

        client.request(request, packet -> {
            if (packet instanceof SDeleteKeywordResponse res && res.isSuccess()) {
                SwingUtilities.invokeLater(() -> {
                    keywords.remove(keyword);
                    updateKeywordList();
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "삭제 실패: " + ((SDeleteKeywordResponse) packet).getMessage());
                });
            }
        });
    }

    private void updateKeywordList() {
        keywordPanel.removeAll();
        for (String keyword : keywords) {
            JPanel keywordItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel keywordLabel = new JLabel("🟦 " + keyword);
            JButton deleteButton = new JButton("❌");
            deleteButton.addActionListener(e -> deleteKeywordFromServer(keyword));
            keywordItemPanel.add(keywordLabel);
            keywordItemPanel.add(deleteButton);
            keywordPanel.add(keywordItemPanel);
        }
        keywordPanel.revalidate();
        keywordPanel.repaint();
    }
} 