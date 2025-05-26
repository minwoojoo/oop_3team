package gui.common;

import gui.auth.LoginScreen;
import gui.chatroom.CreateGroupChatScreen;
import gui.chatroom.GroupChatScreen;
import gui.friend.AddFriendScreen;
import gui.friend.FriendProfileScreen;
import gui.setting.BlockListScreen;
import gui.setting.MemoListScreen;
import gui.setting.ProfileScreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainScreen extends JFrame {
    public static List<String> friendNames;
    private List<String> statusMessages;
    private List<String> chatRoomNames;
    private List<String> lastMessages;
    private List<String> lastMessageTimes;

    public MainScreen() {
        setTitle("메인 화면");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 랜덤 데이터 생성
        friendNames = generateRandomFriendNames();
        statusMessages = generateRandomStatusMessages();
        chatRoomNames = generateRandomChatRoomNames();
        lastMessages = generateRandomLastMessages();
        lastMessageTimes = generateRandomTimes();

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // 상단 앱명 표시
        JLabel appTitle = new JLabel("일톡스", SwingConstants.CENTER);
        appTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(appTitle, BorderLayout.NORTH);

        // 탭 패널 생성
        JTabbedPane tabbedPane = new JTabbedPane();

        // 친구 탭
        JPanel friendPanel = new JPanel(new BorderLayout());
        
        // 상단: 친구 추가 버튼과 검색바
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton addFriendButton = new JButton("➕ 친구 추가");
        JTextField searchField = new JTextField();
        
        topPanel.add(addFriendButton, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        friendPanel.add(topPanel, BorderLayout.NORTH);

        // 중앙: 친구 리스트
        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
        
        for (int i = 0; i < friendNames.size(); i++) {
            final int index = i;
            JPanel friendItemPanel = new JPanel(new BorderLayout());
            friendItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // 상태 점 표시
            JLabel statusDot = new JLabel("●");
            statusDot.setForeground(new Random().nextBoolean() ? Color.GREEN : Color.GRAY);
            
            // 친구 정보
            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            JLabel nameLabel = new JLabel(friendNames.get(index));
            nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            JLabel statusLabel = new JLabel(statusMessages.get(index));
            statusLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            statusLabel.setForeground(Color.GRAY);
            
            infoPanel.add(nameLabel);
            infoPanel.add(statusLabel);

            friendItemPanel.add(statusDot, BorderLayout.WEST);
            friendItemPanel.add(infoPanel, BorderLayout.CENTER);

            // 클릭 이벤트 처리
            friendItemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    FriendProfileScreen profileScreen = new FriendProfileScreen(
                        friendNames.get(index), 
                        statusMessages.get(index)
                    );
                    profileScreen.setVisible(true);
                }
            });

            friendListPanel.add(friendItemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        friendPanel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("친구 👥", friendPanel);

        // 대화 탭
        JPanel chatPanel = new JPanel(new BorderLayout());
        
        // 상단: 그룹 대화방 생성 버튼
        JButton createGroupButton = new JButton("➕ 그룹 대화방 생성");
        chatPanel.add(createGroupButton, BorderLayout.NORTH);

        // 중앙: 대화방 리스트
        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        
        for (int i = 0; i < chatRoomNames.size(); i++) {
            final int index = i;
            JPanel chatItemPanel = new JPanel(new BorderLayout());
            chatItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // 대화방 정보
            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            JLabel nameLabel = new JLabel(chatRoomNames.get(index));
            nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            JLabel messageLabel = new JLabel(lastMessages.get(index));
            messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            messageLabel.setForeground(Color.GRAY);
            
            infoPanel.add(nameLabel);
            infoPanel.add(messageLabel);

            // 시간 표시
            JLabel timeLabel = new JLabel(lastMessageTimes.get(index));
            timeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
            timeLabel.setForeground(Color.GRAY);

            chatItemPanel.add(infoPanel, BorderLayout.CENTER);
            chatItemPanel.add(timeLabel, BorderLayout.EAST);

            // 클릭 이벤트 처리
            chatItemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    GroupChatScreen chatScreen = new GroupChatScreen(
                        chatRoomNames.get(index),
                        new ArrayList<>(friendNames.subList(0, 3))
                    );
                    chatScreen.setVisible(true);
                }
            });

            chatListPanel.add(chatItemPanel);
        }

        JScrollPane chatScrollPane = new JScrollPane(chatListPanel);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("대화 💬", chatPanel);

        // 설정 탭
        JPanel settingsPanel = new JPanel(new BorderLayout(10, 10));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel settingsButtonPanel = new JPanel();
        settingsButtonPanel.setLayout(new BoxLayout(settingsButtonPanel, BoxLayout.Y_AXIS));
        settingsButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton profileButton = new JButton("프로필 설정");
        JButton memoButton = new JButton("메모 조회");
        JButton blockListButton = new JButton("차단 목록");
        JButton logoutButton = new JButton("로그아웃");

        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        memoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        blockListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        profileButton.addActionListener(e -> {
            new ProfileScreen(this).setVisible(true);
        });

        memoButton.addActionListener(e -> {
            new MemoListScreen(this).setVisible(true);
        });

        blockListButton.addActionListener(e -> {
            new BlockListScreen(this).setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                "정말로 로그아웃 하시겠습니까?",
                "로그아웃",
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                dispose();
                new LoginScreen().setVisible(true);
            }
        });

        settingsButtonPanel.add(Box.createVerticalGlue());
        settingsButtonPanel.add(profileButton);
        settingsButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsButtonPanel.add(memoButton);
        settingsButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsButtonPanel.add(blockListButton);
        settingsButtonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsButtonPanel.add(logoutButton);
        settingsButtonPanel.add(Box.createVerticalGlue());

        settingsPanel.add(settingsButtonPanel, BorderLayout.CENTER);
        tabbedPane.addTab("설정", settingsPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // 버튼 이벤트 처리
        addFriendButton.addActionListener(e -> {
            AddFriendScreen addFriendScreen = new AddFriendScreen();
            addFriendScreen.setVisible(true);
        });

        createGroupButton.addActionListener(e -> {
            CreateGroupChatScreen createGroupScreen = new CreateGroupChatScreen();
            createGroupScreen.setVisible(true);
        });

        add(mainPanel);
    }

    private static List<String> generateRandomFriendNames() {
        List<String> names = new ArrayList<>();
        String[] firstNames = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};
        String[] lastNames = {"민준", "서연", "지우", "서준", "하은", "도윤", "시윤", "지아", "하준", "지민"};
        
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String name = firstNames[random.nextInt(firstNames.length)] + 
                         lastNames[random.nextInt(lastNames.length)];
            names.add(name);
        }
        
        return names;
    }

    private List<String> generateRandomStatusMessages() {
        List<String> messages = new ArrayList<>();
        String[] statuses = {
            "업무 중",
            "회의 중",
            "외출",
            "식사 중",
            "여행 중",
            "수면 중"
        };
        
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            messages.add(statuses[random.nextInt(statuses.length)]);
        }
        
        return messages;
    }

    private List<String> generateRandomChatRoomNames() {
        List<String> names = new ArrayList<>();
        String[] prefixes = {"프로젝트", "스터디", "모임", "친구", "가족", "동아리"};
        String[] suffixes = {"방", "그룹", "팀", "모임"};
        
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            String name = prefixes[random.nextInt(prefixes.length)] + 
                         suffixes[random.nextInt(suffixes.length)];
            names.add(name);
        }
        
        return names;
    }

    private List<String> generateRandomLastMessages() {
        List<String> messages = new ArrayList<>();
        String[] sampleMessages = {
            "안녕하세요!",
            "반갑습니다.",
            "오늘 날씨가 좋네요.",
            "어제 영화 재미있었어요.",
            "점심 먹었어요?",
            "주말에 뭐 하실 거예요?",
            "다음 주에 만나요!",
            "좋은 하루 되세요!",
            "수고하셨습니다.",
            "잘 지내고 계신가요?"
        };
        
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            String sender = friendNames.get(random.nextInt(friendNames.size()));
            String message = sampleMessages[random.nextInt(sampleMessages.length)];
            messages.add(sender + ": " + message);
        }
        
        return messages;
    }

    private List<String> generateRandomTimes() {
        List<String> times = new ArrayList<>();
        String[] hours = {"오전", "오후"};
        
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            String time = hours[random.nextInt(hours.length)] + " " +
                         String.format("%d:%02d", random.nextInt(12) + 1, random.nextInt(60));
            times.add(time);
        }
        
        return times;
    }
} 