package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui.AddScheduleScreen;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui.AttendanceScreen;
import kr.ac.catholic.cls032690125.oop3team.client.MainScreen;
import kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside.gui.KeywordSettingsScreen;
import kr.ac.catholic.cls032690125.oop3team.features.memo.clientside.gui.ChatMemoPopup;
import kr.ac.catholic.cls032690125.oop3team.features.schedule.clientside.gui.ScheduleScreen;
import kr.ac.catholic.cls032690125.oop3team.models.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatScreen extends JFrame implements ChatScreenBase {
    private JTextArea chatArea;
    private JTextField inputField;
    private boolean notificationsEnabled = true;
    private String groupName;
    private List<String> members;
    private List<ThreadInfo> threads = new ArrayList<>();
    private JPanel threadPanel;
    private JDialog threadListDialog;

    public GroupChatScreen(String groupName, List<String> members) {
        this.groupName = groupName;
        this.members = members;
        
        // 가짜 스레드 데이터 추가
        threads.add(new ThreadInfo("프로젝트 진행 상황", true));
        threads.add(new ThreadInfo("주간 회의 안건", true));
        threads.add(new ThreadInfo("버그 리포트", true));
        threads.add(new ThreadInfo("기획 회의", false));
        threads.add(new ThreadInfo("디자인 리뷰", false));
        threads.add(new ThreadInfo("QA 테스트 결과", false));
        
        setTitle("그룹 채팅 - " + groupName);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 상단 앱명 표시
        JLabel appTitle = new JLabel("일톡스", SwingConstants.CENTER);
        appTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        appTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
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
        
        // 알림 설정 메뉴 아이템
        JMenuItem notificationItem = new JMenuItem("알림 설정");
        notificationItem.addActionListener(e -> {
            notificationsEnabled = !notificationsEnabled;
            JOptionPane.showMessageDialog(this,
                notificationsEnabled ? "알림이 켜졌습니다." : "알림이 꺼졌습니다.",
                "알림 설정",
                JOptionPane.INFORMATION_MESSAGE);
        });

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
                dispose();
            }
        });

        JMenuItem threadMenuItem = new JMenuItem("스레드");
        threadMenuItem.addActionListener(e -> showThreadList());
        
        menu.add(notificationItem);
        menu.add(attendanceItem);
        menu.add(addScheduleItem);
        menu.add(viewScheduleItem);
        menu.add(keywordSettingsItem);
        menu.add(leaveItem);
        menu.add(threadMenuItem);
        
        menuBar.add(menu);
        rightButtonPanel.add(menuBar);

        topPanel.add(rightButtonPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

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
        inputField.addActionListener(e -> sendMessage());

        // 샘플 메시지 추가
        addRandomMessages();

        add(mainPanel);
    }

    private void showInviteDialog() {
        JDialog inviteDialog = new JDialog(this, "친구 초대", true);
        inviteDialog.setSize(300, 400);
        inviteDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        
        // 친구 목록
        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
        
        List<String> allFriends = MainScreen.friendNames;  // MainScreen의 친구 목록 사용
        List<JCheckBox> checkBoxes = new ArrayList<>();
        
        for (String friend : allFriends) {
            if (!members.contains(friend)) {  // 이미 대화방에 있는 친구는 제외
                JCheckBox checkBox = new JCheckBox(friend);
                checkBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
                checkBoxes.add(checkBox);
                friendListPanel.add(checkBox);
            }
        }

        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 초대 버튼
        JButton inviteButton = new JButton("초대");
        inviteButton.addActionListener(e -> {
            boolean invited = false;
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    String friendName = checkBox.getText();
                    members.add(friendName);
                    chatArea.append("[시스템] " + friendName + "님이 초대되었습니다.\n");
                    invited = true;
                }
            }
            
            if (invited) {
                inviteDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(inviteDialog,
                    "초대할 친구를 선택해주세요.",
                    "알림",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.add(inviteButton, BorderLayout.SOUTH);
        inviteDialog.add(panel);
        inviteDialog.setVisible(true);
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("나: " + message + "\n");
            inputField.setText("");
        }
    }

    private void addRandomMessages() {
        String[] sampleMessages = {
            "안녕하세요!",
            "반갑습니다.",
            "오늘 날씨가 좋네요.",
            "어제 영화 재미있었어요.",
            "점심 먹었어요?",
            "주말에 뭐 하실 거예요?",
            "다음 주에 만나요!",
            "회의는 언제 하나요?",
            "보고서는 언제까지 제출이죠?",
            "개발 예상 기간이 어떻게 되나요?",
            "내일 회의는 15:30분 예정입니다.",
            "좋은 하루 되세요!",
            "수고하셨습니다.",
            "잘 지내고 계신가요?"
        };
        
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            String sender = members.get(random.nextInt(members.size()));
            String message = sampleMessages[random.nextInt(sampleMessages.length)];
            
            // 메시지 패널 생성
            JPanel messagePanel = new JPanel(new BorderLayout());
            messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            
            // 메시지 내용
            JLabel messageLabel = new JLabel(sender + ": " + message);
            messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            
            // 북마크 버튼
            JButton bookmarkButton = new JButton("📌");
            bookmarkButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            bookmarkButton.setBorderPainted(false);
            bookmarkButton.setContentAreaFilled(false);
            bookmarkButton.setFocusPainted(false);
            bookmarkButton.setVisible(false);
            
            // 마우스 이벤트 처리
            messagePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    bookmarkButton.setVisible(true);
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!bookmarkButton.getBounds().contains(e.getPoint())) {
                        bookmarkButton.setVisible(false);
                    }
                }
            });
            
            bookmarkButton.addActionListener(e -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                String timestamp = sdf.format(new Date());
                new ChatMemoPopup(GroupChatScreen.this, message, timestamp).setVisible(true);
            });
            
            messagePanel.add(messageLabel, BorderLayout.WEST);
            messagePanel.add(bookmarkButton, BorderLayout.EAST);
            
            chatArea.append(sender + ": " + message + "\n");
        }
    }

    private void showCreateThreadDialog() {
        JDialog dialog = new JDialog(this, "새 스레드 만들기", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("스레드 제목을 입력하세요");
        JTextField titleField = new JTextField();
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton createButton = new JButton("생성");
        JButton cancelButton = new JButton("취소");

        createButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            if (!title.isEmpty()) {
                threads.add(new ThreadInfo(title, true));
                updateThreadList();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog,
                    "스레드 제목을 입력해주세요.",
                    "입력 오류",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(createButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(titleField, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void showThreadList() {
        if (threadListDialog == null) {
            threadListDialog = new JDialog(this, "스레드 목록", false);
            threadListDialog.setSize(300, 400);
            threadListDialog.setLocationRelativeTo(this);

            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            threadPanel = new JPanel();
            threadPanel.setLayout(new BoxLayout(threadPanel, BoxLayout.Y_AXIS));
            updateThreadList();

            JScrollPane scrollPane = new JScrollPane(threadPanel);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            threadListDialog.add(mainPanel);
        }
        threadListDialog.setVisible(true);
    }

    private void updateThreadList() {
        if (threadPanel == null) return;
        
        threadPanel.removeAll();
        
        // 열린 스레드
        JLabel openLabel = new JLabel("📂 [열린 스레드]");
        openLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        threadPanel.add(openLabel);
        threadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        for (ThreadInfo thread : threads) {
            if (thread.isOpen) {
                JPanel threadItem = new JPanel(new BorderLayout());
                threadItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                
                JLabel titleLabel = new JLabel("📎 " + thread.title);
                titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
                
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem closeItem = new JMenuItem("스레드 닫기");
                closeItem.addActionListener(e -> {
                    thread.isOpen = false;
                    updateThreadList();
                });
                popupMenu.add(closeItem);
                
                threadItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            popupMenu.show(threadItem, e.getX(), e.getY());
                        }
                    }
                });
                
                threadItem.add(titleLabel, BorderLayout.WEST);
                threadPanel.add(threadItem);
                threadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        
        threadPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // 닫힌 스레드
        JLabel closedLabel = new JLabel("📂 [닫힌 스레드]");
        closedLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        threadPanel.add(closedLabel);
        threadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        for (ThreadInfo thread : threads) {
            if (!thread.isOpen) {
                JPanel threadItem = new JPanel(new BorderLayout());
                threadItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                
                JLabel titleLabel = new JLabel("📎 " + thread.title);
                titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
                titleLabel.setForeground(Color.GRAY);
                
                threadItem.add(titleLabel, BorderLayout.WEST);
                threadPanel.add(threadItem);
                threadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        
        threadPanel.revalidate();
        threadPanel.repaint();
    }

    @Override
    public void onChatMessage(Message message) {
        //TODO
        // note: client.getChatReceiver.registerChatroom(this.controller)를 호출해야 메시지를 받을 수 있습니다
    }

    private static class ThreadInfo {
        String title;
        boolean isOpen;

        public ThreadInfo(String title, boolean isOpen) {
            this.title = title;
            this.isOpen = isOpen;
        }
    }
} 