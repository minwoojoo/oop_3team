package kr.ac.catholic.cls032690125.oop3team.features.thread.clientside.gui.dialog;

import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.ThreadChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomThreadListPacket;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.client.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;

public class ThreadListDialog extends JDialog {
    private final CChatroomController chatroomController;
    private final int parentChatroomId;
    private final Client client;
    private final Chatroom parentRoom;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JPanel openThreadListPanel;
    private JPanel closedThreadListPanel;

    public ThreadListDialog(Frame parent, Client client, Chatroom parentRoom, CChatroomController chatroomController) {
        super(parent, "스레드 목록", true);
        this.client = client;
        this.parentRoom = parentRoom;
        this.chatroomController = chatroomController;
        this.parentChatroomId = parentRoom.getChatroomId();
        initializeUI();
        loadThreadList();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 열린 스레드 헤더 (중앙 정렬)
        JLabel openHeader = new JLabel("📂 [열린 스레드]");
        openHeader.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        openHeader.setBorder(new EmptyBorder(5, 0, 5, 0));
        openHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        openHeader.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(openHeader);

        // 열린 스레드 목록 패널
        openThreadListPanel = new JPanel();
        openThreadListPanel.setLayout(new BoxLayout(openThreadListPanel, BoxLayout.Y_AXIS));
        openThreadListPanel.setBackground(Color.WHITE);
        mainPanel.add(openThreadListPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // 닫힌 스레드 헤더 (중앙 정렬)
        JLabel closedHeader = new JLabel("📂 [닫힌 스레드]");
        closedHeader.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        closedHeader.setBorder(new EmptyBorder(5, 0, 5, 0));
        closedHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        closedHeader.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(closedHeader);

        // 닫힌 스레드 목록 패널
        closedThreadListPanel = new JPanel();
        closedThreadListPanel.setLayout(new BoxLayout(closedThreadListPanel, BoxLayout.Y_AXIS));
        closedThreadListPanel.setBackground(Color.WHITE);
        mainPanel.add(closedThreadListPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        setSize(420, 600);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void loadThreadList() {
        openThreadListPanel.removeAll();
        closedThreadListPanel.removeAll();

        // 열린 스레드 로드
        chatroomController.requestThreadRoomList(parentChatroomId, false, response -> {
            if (response.isSuccess()) {
                for (Chatroom thread : response.getThreads()) {
                    openThreadListPanel.add(createThreadItem(thread));
                }
                openThreadListPanel.revalidate();
                openThreadListPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                        "열린 스레드 목록을 불러오는데 실패했습니다: " + response.getErrorMessage(),
                        "오류",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // 닫힌 스레드 로드
        chatroomController.requestThreadRoomList(parentChatroomId, true, response -> {
            if (response.isSuccess()) {
                for (Chatroom thread : response.getThreads()) {
                    closedThreadListPanel.add(createThreadItem(thread));
                }
                closedThreadListPanel.revalidate();
                closedThreadListPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                        "닫힌 스레드 목록을 불러오는데 실패했습니다: " + response.getErrorMessage(),
                        "오류",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JPanel createThreadItem(Chatroom thread) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel titleLabel = new JLabel(thread.getTitle());
        titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        titleLabel.setBorder(new EmptyBorder(0, 8, 0, 0));

        JLabel dateLabel = new JLabel(thread.getCreated().format(formatter) + " 생성");
        dateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        dateLabel.setForeground(Color.GRAY);
        dateLabel.setBorder(new EmptyBorder(0, 0, 0, 8));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(dateLabel, BorderLayout.EAST);

        if (!thread.isClosed()) {
            // 열린 스레드만 마우스 이벤트 추가
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openThreadChatScreen(thread);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    panel.setBackground(new Color(230, 240, 255));
                    panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    panel.setBackground(Color.WHITE);
                    panel.setCursor(Cursor.getDefaultCursor());
                }
            });
        } else {
            // 닫힌 스레드는 흐리게 처리, 마우스 이벤트 없음
            titleLabel.setForeground(Color.LIGHT_GRAY);
            dateLabel.setForeground(new Color(200, 200, 200));
            panel.setBackground(new Color(245, 245, 245));
        }

        return panel;
    }

    // 실제 스레드 채팅방 화면을 여는 메서드
    private void openThreadChatScreen(Chatroom thread) {
        dispose();
        new ThreadChatScreen(client, thread, parentRoom, chatroomController).setVisible(true);
    }
}
