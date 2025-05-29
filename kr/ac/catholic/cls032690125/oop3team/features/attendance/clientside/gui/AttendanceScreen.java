package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceScreen extends JFrame {
    private List<AttendanceRecord> records = new ArrayList<>();
    private JPanel recordListPanel;

    public AttendanceScreen(JFrame parent) {
        setTitle("출퇴근 기록");

        setSize(500, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // 샘플 데이터 추가
        records.add(new AttendanceRecord("2024-03-20", "09:00", "18:00", "7시간 42분"));
        records.add(new AttendanceRecord("2024-03-19", "09:15", "18:30", "7시간 57분"));
        records.add(new AttendanceRecord("2024-03-18", "09:05", "18:15", "7시간 52분"));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 상단 패널 (날짜 선택)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("날짜 선택: ");
        JComboBox<String> dateComboBox = new JComboBox<>(new String[]{"최근 7일", "2024-03-20", "2024-03-19", "2024-03-18"});
        topPanel.add(dateLabel);
        topPanel.add(dateComboBox);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 기록 목록 패널
        recordListPanel = new JPanel();
        recordListPanel.setLayout(new BoxLayout(recordListPanel, BoxLayout.Y_AXIS));
        updateRecordList();

        JScrollPane scrollPane = new JScrollPane(recordListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 하단 패널 (수정 요청 버튼)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton checkInButton = new JButton("출근");
        checkInButton.addActionListener(e -> {
            // TODO: 출근 처리 로직 구현
            JOptionPane.showMessageDialog(this, "출근이 기록되었습니다.");
        });
        JButton editRequestButton = new JButton("기록 수정 요청");
        editRequestButton.addActionListener(e -> {
            new AttendanceEditScreen(this).setVisible(true);
        });
        bottomPanel.add(checkInButton);
        bottomPanel.add(editRequestButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateRecordList() {
        recordListPanel.removeAll();
        
        for (AttendanceRecord record : records) {
            JPanel recordCard = new JPanel(new BorderLayout(5, 5));
            recordCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            recordCard.setBackground(Color.WHITE);
            recordCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            // 기록 정보 패널
            JPanel infoPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            
            JLabel dateLabel = new JLabel("날짜: " + record.getDate());
            dateLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            
            JLabel checkInLabel = new JLabel("출근: " + record.getCheckInTime());
            checkInLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            
            JLabel checkOutLabel = new JLabel("퇴근: " + record.getCheckOutTime());
            checkOutLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            
            JLabel totalLabel = new JLabel("총 근무: " + record.getTotalTime());
            totalLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

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

    public static class AttendanceRecord {
        private String date;
        private String checkInTime;
        private String checkOutTime;
        private String totalTime;

        public AttendanceRecord(String date, String checkInTime, String checkOutTime, String totalTime) {
            this.date = date;
            this.checkInTime = checkInTime;
            this.checkOutTime = checkOutTime;
            this.totalTime = totalTime;
        }

        public String getDate() { return date; }
        public String getCheckInTime() { return checkInTime; }
        public String getCheckOutTime() { return checkOutTime; }
        public String getTotalTime() { return totalTime; }
    }
} 