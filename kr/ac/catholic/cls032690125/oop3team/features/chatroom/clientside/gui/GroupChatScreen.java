package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui.AddScheduleScreen;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui.AttendanceScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.dialog.GroupChatFriendInviteDialog;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomMemberListPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomLeavePacket;
import kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside.gui.KeywordSettingsScreen;
import kr.ac.catholic.cls032690125.oop3team.features.schedule.clientside.gui.ScheduleScreen;
import kr.ac.catholic.cls032690125.oop3team.features.thread.clientside.gui.dialog.ThreadCreateDialog;
import kr.ac.catholic.cls032690125.oop3team.features.thread.clientside.gui.dialog.ThreadListDialog;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.features.friend.clientside.CFriendController;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GroupChatScreen extends JFrame implements ChatScreenBase {
    private JTextArea chatArea;
    private JTextField inputField;

    private boolean notificationsEnabled = true;

    private List<String> members;
    private ArrayList<String> memberNames = new ArrayList<>();
    public void setMembers(List<String> members) {
        this.members = members;
    }
    public List<String> getMembers() { return members; }

    private List<Message> messages;
    private void addMessage(Message message) {
        StringBuilder str = new StringBuilder(chatArea.getText());
        String senderName = userIdToName.getOrDefault(message.getSenderId(), message.getSenderId());
        str.append("[" + senderName + "] " + message.getContent()).append("\n");
        chatArea.setText(str.toString());
    }

    private JDialog threadListDialog;

    private Client client;
    private Chatroom chatroom;
    private CChatroomIndividualController controller;
    public CChatroomIndividualController getController() { return controller; }
    private final CChatroomController chatroomController;
    private List<ThreadInfo> threads = new ArrayList<>();

    private JLabel memberLabel;

    private Map<String, String> userIdToName = new HashMap<>();

    public GroupChatScreen(Client client, Chatroom chatroom) {
        this.client = client;
        this.chatroom = chatroom;
        this.controller = new CChatroomIndividualController(client, chatroom, this);
        this.chatroomController = new CChatroomController(client);

        this.members = new ArrayList<>();
        
        setTitle("그룹 채팅 - " + chatroom.getTitle());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 상단 앱명 표시
        JLabel appTitle = new JLabel("일톡스", SwingConstants.CENTER);
        appTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        mainPanel.add(appTitle, BorderLayout.NORTH);

        // 상단 패널 (알림 설정, 친구 추가, 메뉴)
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 왼쪽 버튼 패널
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        // 스레드 생성 버튼
        JButton createThreadButton = new JButton("스레드 만들기");
        createThreadButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        createThreadButton.addActionListener(e -> showCreateThreadDialog());
        leftButtonPanel.add(createThreadButton);
        
        topPanel.add(leftButtonPanel, BorderLayout.WEST);
        
        // 우측 버튼 패널
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        
        // 친구 추가 버튼
        JButton inviteButton = new JButton("+");
        inviteButton.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        inviteButton.setPreferredSize(new Dimension(30, 20));
        inviteButton.setMargin(new Insets(0, 0, 0, 0));
        inviteButton.setBorderPainted(false);
        inviteButton.setContentAreaFilled(false);
        inviteButton.setFocusPainted(false);
        inviteButton.addActionListener(e -> showInviteDialog());
        rightButtonPanel.add(inviteButton);

        // 메뉴바
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("메뉴");

        // 출퇴근 기록 메뉴 아이템
        JMenuItem attendanceItem = new JMenuItem("출퇴근 기록");
        attendanceItem.addActionListener(e -> {
            new AttendanceScreen(this).setVisible(true);
        });
        
        // 일정 관련 메뉴 아이템
        JMenuItem addScheduleItem = new JMenuItem("일정 등록");
        addScheduleItem.addActionListener(e -> {
            new AddScheduleScreen(this).setVisible(true);
        });

        JMenuItem viewScheduleItem = new JMenuItem("일정 보기");
        viewScheduleItem.addActionListener(e -> {
            new ScheduleScreen(this).setVisible(true);
        });

        // 우선 알림 키워드 설정 메뉴 아이템
        JMenuItem keywordSettingsItem = new JMenuItem("우선 알림 키워드 설정");
        keywordSettingsItem.addActionListener(e -> {
            new KeywordSettingsScreen(this).setVisible(true);
        });

        // 나가기 메뉴 아이템
        JMenuItem leaveItem = new JMenuItem("나가기");
        leaveItem.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                "정말로 대화방에서 나가시겠습니까?",
                "대화방 나가기",
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                chatroomController.requestLeaveChatroom(
                    chatroom.getChatroomId(),
                    client.getCurrentSession().getUserId(),
                    new ClientInteractResponseSwing<SChatroomLeavePacket>() {
                        @Override
                        protected void execute(SChatroomLeavePacket data) {
                            try {
                                SwingUtilities.invokeLater(() -> {
                                    String myName = getUserNameById(client.getCurrentSession().getUserId());
                                    addSystemMessage(myName + "님이 나갔습니다");
                                    fetchAndStoreMembers();
                                    JOptionPane.showMessageDialog(GroupChatScreen.this, data.getMessage());
                                });
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    
                );
            }
            dispose();
        });

        JMenuItem threadMenuItem = new JMenuItem("스레드");
        threadMenuItem.addActionListener(e -> showThreadList());

        menu.add(attendanceItem);
        menu.add(addScheduleItem);
        menu.add(viewScheduleItem);
        menu.add(keywordSettingsItem);
        menu.add(leaveItem);
        menu.add(threadMenuItem);
        
        menuBar.add(menu);
        rightButtonPanel.add(menuBar);

        topPanel.add(rightButtonPanel, BorderLayout.EAST);

        // 멤버 목록 라벨 추가
        memberLabel = new JLabel("멤버: ");
        memberLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));

        // 멤버 라벨을 감싸는 패널 생성
        JPanel memberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        memberPanel.setBackground(new Color(250, 250, 250)); // 밝은 배경
        memberPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true), // 둥근 테두리
            BorderFactory.createEmptyBorder(5, 10, 5, 10) // 내부 패딩
        ));
        memberPanel.add(memberLabel);

        // topPanel과 memberPanel을 묶어서 상단에 배치
        JPanel topWithMemberPanel = new JPanel();
        topWithMemberPanel.setLayout(new BoxLayout(topWithMemberPanel, BoxLayout.Y_AXIS));
        topWithMemberPanel.add(topPanel);
        topWithMemberPanel.add(memberPanel);
        mainPanel.add(topWithMemberPanel, BorderLayout.BEFORE_FIRST_LINE);

        // 상단 일정 박스 추가
        JPanel scheduleBox = new JPanel(new BorderLayout(5, 5));
        scheduleBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scheduleBox.setBackground(Color.WHITE);
        
        // 샘플 일정 데이터
        JLabel scheduleTitle = new JLabel("주간 회의");
        scheduleTitle.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        JLabel scheduleDateTime = new JLabel("2024-03-20 14:00");
        scheduleDateTime.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        
        JPanel scheduleInfo = new JPanel(new BorderLayout(5, 0));
        scheduleInfo.add(scheduleTitle, BorderLayout.NORTH);
        scheduleInfo.add(scheduleDateTime, BorderLayout.SOUTH);
        
        scheduleBox.add(scheduleInfo, BorderLayout.CENTER);
        scheduleBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ScheduleScreen(GroupChatScreen.this).setVisible(true);
            }
        });
        
        // 일정 박스를 별도의 패널에 추가
        JPanel schedulePanel = new JPanel(new BorderLayout());
        schedulePanel.add(scheduleBox, BorderLayout.NORTH);
        mainPanel.add(schedulePanel, BorderLayout.CENTER);

        // 채팅 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        chatArea.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        schedulePanel.add(scrollPane, BorderLayout.CENTER);

        // 입력 영역
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        JButton sendButton = new JButton("전송");
        sendButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // 이벤트 처리
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage()); //TODO: WHAT IS THIS???

        add(mainPanel);
    }

    private void showInviteDialog() {
        var invd = new GroupChatFriendInviteDialog(client, controller, this);
        invd.setVisible(true);
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            controller.sendMessage(message, new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                @Override
                protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                    if(data.getData())
                        inputField.setText("");
                    else
                        inputField.setText("오류가 발생했습니다"); //TODO do better
                }
            });
        }
    }

    private void showCreateThreadDialog() {
        var dialog = new ThreadCreateDialog(client, this);
        dialog.setVisible(true);
    }

    private void showThreadList() {
        ThreadListDialog dialog = new ThreadListDialog(
            this,                // Frame parent
            client,              // Client 객체
            chatroom,            // 현재 그룹채팅방 Chatroom 객체 (부모방)
            chatroomController   // 컨트롤러
        );
        dialog.setVisible(true);
    }

    @Override
    public void setVisible(boolean b) {
        if(b) initiate();
        super.setVisible(b);
    }

    @Override
    public void initiate() {
        System.out.println("initiate");
        client.getChatReceiver().registerChatroom(controller);
        fetchAndStoreMembers(); // 참가자 가져오기
        controller.initiateMessage(1000000, new ClientInteractResponseSwing<SMessageLoadPacket>() {
            @Override
            protected void execute(SMessageLoadPacket data) {
                StringBuilder str = new StringBuilder();
                var msgs = Arrays.asList(data.getMessages());
                for(Message message : msgs) {
                    String senderName = userIdToName.getOrDefault(message.getSenderId(), message.getSenderId());
                    str.append("[" + senderName + "] " + message.getContent()).append("\n");
                }
                messages = msgs;
                str.append(chatArea.getText());
                chatArea.setText(str.toString());
            }
        });
        //TODO: 이전 채팅 불러오기, 스레드 , 메모, 맴버 불러오기 등
    }

    @Override
    public void onChatMessage(Message message) {
        addMessage(message);
    }

    private void updateMemberLabel() {
        if (members == null || members.isEmpty()) {
            memberLabel.setText("멤버: (없음)");
            return;
        }
        String memberNames = String.join(", ", members);
        memberLabel.setText("멤버: " + memberNames);
    }

    private void fetchFriendProfilesAndUpdateMemberLabel() {
        CFriendController friendController = new CFriendController(client);
        friendController.getFriendList(client.getCurrentSession().getUserId(), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
            @Override
            protected void execute(ServerResponsePacketSimplefied<UserProfile[]> response) {
                userIdToName.clear();
                if (response.getData() != null) {
                    for (UserProfile profile : response.getData()) {
                        userIdToName.put(profile.getUserId(), profile.getName());
                    }
                }
                updateMemberLabelWithNames();
            }
        });
    }

    private void updateMemberLabelWithNames() {
        if (memberNames == null || memberNames.isEmpty()) {
            memberLabel.setText("멤버: (없음)");
            return;
        }
        memberLabel.setText("멤버: " + String.join(", ", memberNames));
    }

    public void fetchAndStoreMembers() {
        chatroomController.getMemberListWithNames(
            chatroom.getChatroomId(),
            new ClientInteractResponseSwing<SChatroomMemberListPacket>() {
                @Override
                protected void execute(SChatroomMemberListPacket data) {
                    setMembers(data.getMembers());
                    setMemberNames(data.getMemberNames());
                    // id→name 매핑
                    userIdToName.clear();
                    for (int i = 0; i < data.getMembers().size(); i++) {
                        userIdToName.put(data.getMembers().get(i), data.getMemberNames().get(i));
                    }
                    updateMemberLabelWithNames();
                }
            }
        );
    }

    @Deprecated
    private static class ThreadInfo {
        String title;
        boolean isOpen;
        Chatroom newThreadRoom;

        public ThreadInfo(String title, boolean isOpen, Chatroom newThreadRoom) {
            this.title = title;
            this.isOpen = isOpen;
            this.newThreadRoom = newThreadRoom;
        }
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void addNewThread(Chatroom newThreadRoom, String threadTitle) {
        threads.add(new ThreadInfo(threadTitle, true, newThreadRoom));
    }

    public void addSystemMessage(String msg) {
        StringBuilder str = new StringBuilder(chatArea.getText());
        str.append("[system] ").append(msg).append("\n");
        chatArea.setText(str.toString());
    }

    public String getUserNameById(String userId) {
        return userIdToName.getOrDefault(userId, userId);
    }

    public void setMemberNames(ArrayList<String> memberNames) {
        this.memberNames = memberNames;
    }
}


//// 메시지 패널 생성
//JPanel messagePanel = new JPanel(new BorderLayout());
//            messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
//
//// 메시지 내용
//JLabel messageLabel = new JLabel(sender + ": " + message);
//            messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
//
//// 북마크 버튼
//JButton bookmarkButton = new JButton("📌");
//            bookmarkButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
//        bookmarkButton.setBorderPainted(false);
//            bookmarkButton.setContentAreaFilled(false);
//            bookmarkButton.setFocusPainted(false);
//            bookmarkButton.setVisible(false);
//
//// 마우스 이벤트 처리
//            messagePanel.addMouseListener(new MouseAdapter() {
//    @Override
//    public void mouseEntered(MouseEvent e) {
//        bookmarkButton.setVisible(true);
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//        if (!bookmarkButton.getBounds().contains(e.getPoint())) {
//            bookmarkButton.setVisible(false);
//        }
//    }
//});
//
//        bookmarkButton.addActionListener(e -> {
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
//String timestamp = sdf.format(new Date());
//                new ChatMemoPopup(GroupChatScreen.this, message, timestamp).setVisible(true);
//            });
//
//                    messagePanel.add(messageLabel, BorderLayout.WEST);
//            messagePanel.add(bookmarkButton, BorderLayout.EAST);
//            messagePanel.add(bookmarkButton, BorderLayout.EAST);