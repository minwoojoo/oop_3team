package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.serverside.AttendanceDAO;
import kr.ac.catholic.cls032690125.oop3team.models.Attendance;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.server.Server;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceScreen extends JFrame {
    private JPanel recordListPanel;
    private AttendanceDAO attendanceDAO;
    private Client client;
    private Server server;
    private Chatroom chatroom;

    public AttendanceScreen(JFrame parent, Client client, Server server, Chatroom chatroom) throws SQLException {
        this.client = client;
        this.server = server;
        this.chatroom = chatroom;
        this.attendanceDAO = new AttendanceDAO(server);

        setTitle("출퇴근 기록");
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 상단 패널 (날짜 선택)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("최근 기록 보기"));
        mainPanel.add(topPanel, BorderLayout.NORTH);
//        JLabel dateLabel = new JLabel("날짜 선택: ");
//        JComboBox<String> dateComboBox = new JComboBox<>(new String[]{"최근 7일", "2024-03-20", "2024-03-19", "2024-03-18"});
//        topPanel.add(dateLabel);
//        topPanel.add(dateComboBox);
//        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center panel
        recordListPanel = new JPanel();
        recordListPanel.setLayout(new BoxLayout(recordListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(recordListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        //  updateRecordList();

        // Bottom panel
        // 하단 패널 (수정 요청 버튼)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton checkInButton = new JButton("출근"); //check in
        JButton checkOutButton = new JButton("퇴근"); //check out

        checkInButton.addActionListener(e -> {
            // TODO: 출근 처리 로직 구현
            try {
                attendanceDAO.checkIn(client.getCurrentSession().getUserId());
                JOptionPane.showMessageDialog(this, "출근이 기록되었습니다.");
                updateRecordList();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "출근 기록 실패: " + ex.getMessage());
            }
        });

        checkOutButton.addActionListener(e -> {
            try {
                attendanceDAO.checkOut(client.getCurrentSession().getUserId());
                JOptionPane.showMessageDialog(this, "퇴근이 기록되었습니다.");
                updateRecordList();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "퇴근 기록 실패: " + ex.getMessage(),
                        "오류", JOptionPane.ERROR_MESSAGE);
            }
        });


        JButton editRequestButton = new JButton("기록 수정 요청");
        editRequestButton.addActionListener(e -> {
            new AttendanceEditScreen(this,client,server).setVisible(true);
        });

        bottomPanel.add(checkInButton);
        bottomPanel.add(checkOutButton);
        bottomPanel.add(editRequestButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        updateRecordList();
    }

    private void updateRecordList() throws SQLException {
        recordListPanel.removeAll();
        List<Attendance> records = attendanceDAO.getAttendanceByUserId(client.getCurrentSession().getUserId());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Attendance record : records) {
            JPanel recordCard = new JPanel(new BorderLayout(5, 5));
            recordCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            recordCard.setBackground(Color.WHITE);
            recordCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            // 기록 정보 패널
            JPanel infoPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            JLabel dateLabel = new JLabel("날짜: " + record.getCheckInTime().toLocalDateTime().format(timeFormatter));
//            dateLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

            JLabel checkInLabel = new JLabel("출근: " +
                    (record.getCheckInTime() != null ? record.getCheckInTime().toLocalDateTime().format(timeFormatter) : "")
            );
//            checkInLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

            JLabel checkOutLabel = new JLabel("퇴근: " +
                    (record.getCheckOutTime() != null ? record.getCheckOutTime().toLocalDateTime().format(timeFormatter) : "")
            );

            int totalMinutes = record.getWorkTimeTotal();
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
            String formattedWorkTime = (hours > 0 ? hours + "시간 " : "") + minutes + "분";
            JLabel totalLabel = new JLabel("총 근무: " + formattedWorkTime);


//            JLabel totalLabel = new JLabel("총 근무: " + record.getWorkTimeTotal() + "분");


            infoPanel.add(dateLabel);
            infoPanel.add(totalLabel);
            infoPanel.add(checkInLabel);
            infoPanel.add(checkOutLabel);

            recordCard.add(infoPanel, BorderLayout.CENTER);
            recordListPanel.add(recordCard);
            recordListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        recordListPanel.revalidate();
        recordListPanel.repaint();
    }

//    public static class AttendanceRecord {
//        private String date;
//        private String checkInTime;
//        private String checkOutTime;
//        private String totalTime;
//
//        public AttendanceRecord(String date, String checkInTime, String checkOutTime, String totalTime) {
//            this.date = date;
//            this.checkInTime = checkInTime;
//            this.checkOutTime = checkOutTime;
//            this.totalTime = totalTime;
//        }
//
//        public String getDate() {
//            return date;
//        }
//
//        public String getCheckInTime() {
//            return checkInTime;
//        }
//
//        public String getCheckOutTime() {
//            return checkOutTime;
//        }
//
//        public String getTotalTime() {
//            return totalTime;
//        }
//    }
}