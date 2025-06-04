package gui.friend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddFriendScreen extends JFrame {
    private JTextField searchField;
    private JPanel resultPanel;
    private List<String> friendNames;

    public AddFriendScreen() {
        setTitle("친구 추가");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 랜덤 친구 이름 생성
        friendNames = generateRandomFriendNames();

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
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // 결과 영역
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 검색 버튼 이벤트 처리
        searchButton.addActionListener(e -> searchFriends());
        searchField.addActionListener(e -> searchFriends());

        add(mainPanel);
    }

    private void searchFriends() {
        String searchText = searchField.getText().toLowerCase();
        resultPanel.removeAll();

        for (String name : friendNames) {
            if (name.toLowerCase().contains(searchText)) {
                JPanel friendPanel = new JPanel(new BorderLayout());
                friendPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JLabel nameLabel = new JLabel(name);
                nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

                JButton addButton = new JButton("➕ 추가");

                friendPanel.add(nameLabel, BorderLayout.CENTER);
                friendPanel.add(addButton, BorderLayout.EAST);

                addButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, 
                        name + "님을 친구로 추가했습니다.", 
                        "친구 추가", 
                        JOptionPane.INFORMATION_MESSAGE);
                });

                resultPanel.add(friendPanel);
            }
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private List<String> generateRandomFriendNames() {
        List<String> names = new ArrayList<>();
        String[] firstNames = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};
        String[] lastNames = {"민준", "서연", "지우", "서준", "하은", "도윤", "시윤", "지아", "하준", "지민"};
        
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            String name = firstNames[random.nextInt(firstNames.length)] + 
                         lastNames[random.nextInt(lastNames.length)];
            names.add(name);
        }
        
        return names;
    }
} 