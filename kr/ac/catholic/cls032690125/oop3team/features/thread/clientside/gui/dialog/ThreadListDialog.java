package kr.ac.catholic.cls032690125.oop3team.features.thread.clientside.gui.dialog;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomController;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.GroupChatScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomThreadClosePacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomThreadListPacket;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThreadListDialog extends JDialog {
    private  Client client;
    private  GroupChatScreen groupChatScreen;
    private  CChatroomController cChatroomController;

    private JPanel threadPanel;
    private Chatroom parentRoom;
    private int parentId;

    private void addThreadOpenedHeader() {
        JLabel openLabel = new JLabel("📂 [열린 스레드]");
        openLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        threadPanel.add(openLabel);
        threadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }
    private void addThreadOpened(Chatroom chatroom) {
        if(chatroom.getParentroomId() == 0 || chatroom.isClosed()) return;
        JPanel threadItem = new JPanel(new BorderLayout());
        threadItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel titleLabel = new JLabel("📎 " + chatroom.getTitle());
        titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem closeItem = new JMenuItem("스레드 닫기");
        closeItem.addActionListener(e -> {
            cChatroomController.requestThreadRoomClose(chatroom.getChatroomId(), new ClientInteractResponseSwing<SChatroomThreadClosePacket>() {
                @Override
                protected void execute(SChatroomThreadClosePacket data) {
                    System.out.println("스레드 닫음, 스레드 ID: " + data.getThreadId());
                }
            });
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

    private void addThreadClosedHeader() {
        threadPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        // 닫힌 스레드
        JLabel closedLabel = new JLabel("📂 [닫힌 스레드]");
        closedLabel.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        threadPanel.add(closedLabel);
        threadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }
    private void addThreadClosed(Chatroom chatroom) {
        JPanel threadItem = new JPanel(new BorderLayout());
        threadItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel titleLabel = new JLabel("📎 " + chatroom.getTitle());
        titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        titleLabel.setForeground(Color.GRAY);

        threadItem.add(titleLabel, BorderLayout.WEST);
        threadPanel.add(threadItem);
        threadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    public ThreadListDialog(Client client, GroupChatScreen screen) {
        super(screen, "스레드 목록", false);
        this.client = client;
        this.groupChatScreen = screen;
        this.cChatroomController = new CChatroomController(client);

        setSize(300, 400);
        setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        threadPanel = new JPanel();
        threadPanel.setLayout(new BoxLayout(threadPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(threadPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            initiate();
        }
    }

    private void initiate() {
        this.parentRoom = groupChatScreen.getChatroom();
        parentId = parentRoom.getChatroomId();

        // 열린 스레드 목록 조회
        groupChatScreen.getController().getThread(parentId, false, new ClientInteractResponseSwing<SChatroomThreadListPacket>() {
            @Override
            protected void execute(SChatroomThreadListPacket data) {
                addThreadOpenedHeader();
                for(var d : data.getThread()) addThreadOpened(d);
            }
        });

        //닫힌 스레드 목록 조회 (변수명 실수 입니다;)

        groupChatScreen.getController().getThread(parentId, true, new ClientInteractResponseSwing<SChatroomThreadListPacket>() {
            @Override
            protected void execute(SChatroomThreadListPacket data) {
                addThreadClosedHeader();
                for(var d : data.getThread()) addThreadClosed(d);
            }
        });



    }
}
