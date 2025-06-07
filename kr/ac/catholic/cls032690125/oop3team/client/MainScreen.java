package kr.ac.catholic.cls032690125.oop3team.client;

import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.auth.clientside.CAuthController;
import kr.ac.catholic.cls032690125.oop3team.features.auth.clientside.gui.LoginScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.CreateGroupChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.GroupChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomListPacket;
import kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.gui.AddFriendScreen;
import kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.gui.FriendProfileScreen;
import kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui.BlockListScreen;
import kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui.MemoListScreen;
import kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui.ProfileScreen;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.CFriendController;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.SFriendPendingRes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScreen extends JFrame {
    public List<String> friendNames = new ArrayList<>(); // TODO: 없앨덧
    private List<String> statusMessages;
    private List<String> chatRoomNames;
    private List<String> lastMessages;
    private List<String> lastMessageTimes;
    private Timer sessionTimer;
    private Client client;
    private String userId;

    private CChatroomController chatRoomController;
    private CAuthController authController;
    private JPanel chatListPanel;
    private CFriendController cFriendController;
    private JPanel friendListPanel;
    private List<UserProfile> friendProfiles = new ArrayList<>();

    public MainScreen(String userId, Client client) {
        this.userId = userId;
        this.client = client;
        chatRoomController = new CChatroomController(client);
        authController = new CAuthController(client);
        cFriendController = new CFriendController(client);
        setTitle("메인 화면");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //statusMessages = generateRandomStatusMessages();
        //chatRoomNames = generateRandomChatRoomNames();
        //lastMessages = generateRandomLastMessages();
        //lastMessageTimes = generateRandomTimes();

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
        friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));

        /**
         * 친구 목록 불러오기
         * */
        System.out.println("▶ MainScreen: getFriendList 호출 직전");
        cFriendController.getFriendList(userId, new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
            @Override
            protected void execute(ServerResponsePacketSimplefied<UserProfile[]> response) {
                System.out.println("▶ MainScreen: execute 진입 → friends=" + Arrays.toString(response.getData()));

                UserProfile[] friends = response.getData();
                System.out.println("friends = " + Arrays.toString(friends));
                if (friends != null) {
                    friendNames.clear();
                    friendProfiles.clear();
                    for (UserProfile friend : friends) {
                        friendNames.add(friend.getUserId());
                        friendProfiles.add(friend);
                    }
                    updateFriendListUI(friendListPanel);
                }
            }
        });
        
//        for (int i = 0; i < friendNames.size(); i++) {
//            System.out.println("friendNames.get(i) = " + friendNames.get(i));
//            final int index = i;
//            JPanel friendItemPanel = new JPanel(new BorderLayout());
//            friendItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//
//            // 상태 점 표시
//            JLabel statusDot = new JLabel("●");
//            statusDot.setForeground(new Random().nextBoolean() ? Color.GREEN : Color.GRAY);
//
//            // 친구 정보
//            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
//            JLabel nameLabel = new JLabel(friendNames.get(index));
//            nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
//            JLabel statusLabel = new JLabel(statusMessages.get(index));
//            statusLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
//            statusLabel.setForeground(Color.GRAY);
//
//            infoPanel.add(nameLabel);
//            infoPanel.add(statusLabel);
//
//            friendItemPanel.add(statusDot, BorderLayout.WEST);
//            friendItemPanel.add(infoPanel, BorderLayout.CENTER);
//
//            // 클릭 이벤트 처리
//            friendItemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    FriendProfileScreen profileScreen = new FriendProfileScreen(
//                        friendNames.get(index),
//                        statusMessages.get(index)
//                    );
//                    profileScreen.setVisible(true);
//                }
//            });
//
//            friendListPanel.add(friendItemPanel);
//        }

        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        friendPanel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("친구 👥", friendPanel);

        // 대화 탭
        JPanel chatPanel = new JPanel(new BorderLayout());
        
        // 상단: 그룹 대화방 생성 버튼
        JButton createGroupButton = new JButton("➕ 그룹 대화방 생성");
        chatPanel.add(createGroupButton, BorderLayout.NORTH);

        // 중앙: 대화방 리스트
        chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));


        loadGroupChat();

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
            new ProfileScreen(this, client, userId).setVisible(true);
        });

        memoButton.addActionListener(e -> {
            new MemoListScreen(this).setVisible(true);
        });

        blockListButton.addActionListener(e -> {
            BlockListScreen blockListScreen = new BlockListScreen(this, client, userId);
            blockListScreen.setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                "정말로 로그아웃 하시겠습니까?",
                "로그아웃",
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                authController.sendLogout(new ClientInteractResponseSwing<>() {
                    @Override
                    protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                        if (sessionTimer != null) sessionTimer.stop();
                        new LoginScreen(client).setVisible(true);
                        dispose();
                    }
                });

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
            AddFriendScreen addFriendScreen = new AddFriendScreen(userId, client);
            addFriendScreen.setVisible(true);
        });

        createGroupButton.addActionListener(e -> {
            cFriendController.getFriendList(client.getCurrentSession().getUserId(), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
                @Override
                protected void execute(ServerResponsePacketSimplefied<UserProfile[]> data) {
                    CreateGroupChatScreen createGroupScreen = new CreateGroupChatScreen(client, List.of(data.getData()));
                    createGroupScreen.setVisible(true);
                }
            });
            //CreateGroupChatScreen createGroupScreen = new CreateGroupChatScreen(client);
            //createGroupScreen.setVisible(true);
        });

        add(mainPanel);

        // 세션 만료 체크 타이머 (1분마다 체크)
        sessionTimer = new Timer(60 * 1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var request2 = new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                    @Override
                    protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                        sessionTimer.stop();
                        refreshFriendList();
                        dispose();
                        new LoginScreen(client).setVisible(true);
                    }
                };
                var request1 = new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Session>>() {
                    @Override
                    protected void execute(ServerResponsePacketSimplefied<Session> data) {
                        if(data.getData() == null) {
                            JOptionPane.showMessageDialog(MainScreen.this, "세션이 만료되어 로그아웃됩니다.");
                            authController.sendLogout(request2);
                        }
                    }
                };
                authController.refreshSession(request1);
            }
        });
        sessionTimer.start();

        // 로그인 성공 후
        CFriendController friendController = new CFriendController(client);
        System.out.println("클라이언트: 친구 요청 목록 요청 전송: " + userId);
        friendController.getPendingFriendRequests(userId, new ClientInteractResponseSwing<SFriendPendingRes>() {
            @Override
            protected void execute(SFriendPendingRes data) {
                if (data.getPendingRequests() != null && data.getPendingRequests().length > 0) {
                    for (UserProfile requester : data.getPendingRequests()) {
                        int result = JOptionPane.showConfirmDialog(
                            MainScreen.this,
                            requester.getName() + "님이 친구 요청을 보냈습니다. 수락하시겠습니까?",
                            "친구 요청",
                            JOptionPane.YES_NO_OPTION
                        );
                        if (result == JOptionPane.YES_OPTION) {
                            friendController.acceptFriendRequest(userId, requester.getUserId());
                            JOptionPane.showMessageDialog(MainScreen.this, requester.getName() + "님을 친구로 추가했습니다.");
                            refreshFriendList(); // 친구 목록 새로고침
                        } else {
                            friendController.rejectFriendRequest(userId, requester.getUserId());
                            JOptionPane.showMessageDialog(MainScreen.this, requester.getName() + "님의 친구 요청을 거절했습니다.");
                        }
                    }
                }
            }
        });

        // 친구 목록 자동 새로고침 타이머 (20초마다)
        Timer friendListTimer = new Timer(20 * 1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshFriendList();
            }
        });
        friendListTimer.start();
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

    private void updateFriendListUI(JPanel friendListPanel) {
        friendListPanel.removeAll();

        for (int i = 0; i < friendProfiles.size(); i++) {
            final int index = i;
            JPanel friendItemPanel = new JPanel(new BorderLayout());
            friendItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel statusDot = new JLabel("●");
            statusDot.setForeground(friendProfiles.get(index).isOnline() ? Color.GREEN : Color.GRAY);

            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            JLabel nameLabel = new JLabel(friendProfiles.get(index).getName());
            nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

            // 업무상태(workStatus) 표시
            String workStatus = null;
            try {
                workStatus = friendProfiles.get(index).getWorkStatus();
            } catch (Exception e) { workStatus = null; }
            JLabel statusLabel = new JLabel(
                (workStatus != null && !workStatus.isEmpty()) ? workStatus : "상태 메시지 없음"
            );
            statusLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            statusLabel.setForeground(Color.GRAY);

            infoPanel.add(nameLabel);
            infoPanel.add(statusLabel);

            friendItemPanel.add(statusDot, BorderLayout.WEST);
            friendItemPanel.add(infoPanel, BorderLayout.CENTER);

            friendItemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    UserProfile friendProfile = friendProfiles.get(index);
                    new FriendProfileScreen(client, userId, friendProfile, MainScreen.this).setVisible(true);
                }
            });

            friendListPanel.add(friendItemPanel);
        }

        friendListPanel.revalidate();
        friendListPanel.repaint();
    }

    public void refreshFriendList() {
        System.out.println("▶ MainScreen: getFriendList 호출 직전");
        cFriendController.getFriendList(userId, new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
            @Override
            protected void execute(ServerResponsePacketSimplefied<UserProfile[]> response) {
                System.out.println("▶ MainScreen: execute 진입 → friends=" + Arrays.toString(response.getData()));

                UserProfile[] friends = response.getData();
                System.out.println("friends = " + Arrays.toString(friends));
                if (friends != null) {
                    friendNames.clear();
                    friendProfiles.clear();
                    for (UserProfile friend : friends) {
                        friendNames.add(friend.getUserId());
                        friendProfiles.add(friend);
                    }
                    updateFriendListUI(friendListPanel);
                }
            }
        });
    }

    /**private List<String> generateRandomLastMessages() {
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
    }*/

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

    public void initiate() {
        loadGroupChat();



        this.setVisible(true);
    }

    private void loadGroupChat() {
        chatRoomController.requestChatroomList(false, new ClientInteractResponseSwing<SChatroomListPacket>() {
            @Override
            protected void execute(SChatroomListPacket data) {
                Chatroom[] rooms = data.getRooms();
                if (rooms == null || rooms.length == 0) {
                    System.out.println("참여 중인 채팅방 없음");
                    return;
                }

                for (int i = 0; i < data.getRooms().length; i++) {
                    Chatroom room = data.getRooms()[i];

                    JPanel chatItemPanel = new JPanel(new BorderLayout());
                    chatItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    // 대화방 정보
                    JPanel infoPanel = new JPanel(new GridLayout(2, 1));
                    JLabel nameLabel = new JLabel(room.getTitle());
                    nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
//                    JLabel messageLabel = new JLabel();
//                    messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
//                    messageLabel.setForeground(Color.GRAY);

                    infoPanel.add(nameLabel);
//                    infoPanel.add(messageLabel);

                    // 시간 표시
//                    JLabel timeLabel = new JLabel();
//                    timeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
//                    timeLabel.setForeground(Color.GRAY);

                    chatItemPanel.add(infoPanel, BorderLayout.CENTER);
//                    chatItemPanel.add(timeLabel, BorderLayout.EAST);

                    // 클릭 이벤트 처리
                    chatItemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            GroupChatScreen chatScreen = new GroupChatScreen(client, room);
                            chatScreen.setVisible(true);
                        }
                    });

                    chatListPanel.add(chatItemPanel);
                }
            }
        });
    }
}