package kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.CFriendController;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.SFriendInviteRes;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddFriendScreen extends JFrame {
    private JTextField searchField;
    private JPanel resultPanel;
    private List<String> friendNames;
    private String userId;
    private Client client;

    public AddFriendScreen(String userId, Client client) {
        setTitle("친구 추가");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.userId = userId;
        this.client = client;


        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 상단 앱명 표시
        JLabel appTitle = new JLabel("일톡스", SwingConstants.CENTER);
        appTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(appTitle, BorderLayout.NORTH);

        // 검색 영역
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JButton searchButton = new JButton("검색");
        
        // 안내 메시지 추가
        JLabel guideLabel = new JLabel("친구의 아이디를 입력해주세요");
        guideLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        guideLabel.setForeground(Color.BLACK);
        guideLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.add(searchField, BorderLayout.CENTER);
        searchContainer.add(searchButton, BorderLayout.EAST);
        
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.add(searchContainer);
        searchPanel.add(guideLabel);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // 결과 영역
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 검색 버튼 이벤트 처리
        searchButton.addActionListener(e -> {
            String search = searchField.getText().trim();
            if (search.isEmpty()) return;
            

            CFriendController friendController = new CFriendController(client);
            friendController.searchFriendForInvite(search, new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
                @Override
                protected void execute(ServerResponsePacketSimplefied<UserProfile[]> data) {
                    System.out.println("서버에서 받은 데이터: " + (data.getData() == null ? "null" : data.getData().length));
                    resultPanel.removeAll();
                    if (data.getData() != null && data.getData().length > 0) {
                        for (UserProfile user : data.getData()) {
                            System.out.println("userId: " + user.getUserId() + ", name: " + user.getName());
                            JPanel friendPanel = new JPanel(new BorderLayout());
                            JLabel nameLabel = new JLabel(user.getName() + " (" + user.getUserId() + ")");
                            JButton addButton = new JButton("➕ 추가");
                            friendPanel.add(nameLabel, BorderLayout.CENTER);
                            friendPanel.add(addButton, BorderLayout.EAST);

                            addButton.addActionListener(ev -> {
                                // 친구 추가 로직
                                friendController.inviteFriend(userId, user.getUserId(), new ClientInteractResponseSwing<SFriendInviteRes>() {
                                    @Override
                                    protected void execute(SFriendInviteRes data) {
                                        JOptionPane.showMessageDialog(AddFriendScreen.this, data.getMessage());
                                        if (data.isSuccess()) {
                                            dispose();
                                        }
                                    }
                                });
                            });

                            resultPanel.add(friendPanel);
                        }
                    } else {
                        resultPanel.add(new JLabel("검색 결과가 없습니다."));
                    }
                    resultPanel.revalidate();
                    resultPanel.repaint();
                }
            });
        });

        add(mainPanel);
    }
} 