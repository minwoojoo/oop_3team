package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Attendance;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceScreen extends JFrame {
    private JPanel recordListPanel;
    private Client client;
    private Chatroom chatroom;

    public AttendanceScreen(JFrame parent, Client client, Chatroom chatroom) {
        this.client = client;
        this.chatroom = chatroom;

        setTitle("출퇴근 기록");
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 상단 패널 (Label 제목)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("최근 기록 보기"));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 중앙 스크롤 영역 (출퇴근 기록 리스트)
        recordListPanel = new JPanel();
        recordListPanel.setLayout(new BoxLayout(recordListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(recordListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼 패널 (출근 / 퇴근 / 수정 요청)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton checkInButton = new JButton("출근");
        JButton checkOutButton = new JButton("퇴근");
        JButton editRequestButton = new JButton("기록 수정 요청");

        // 출근 버튼 클릭 처리
        checkInButton.addActionListener(e -> {
            String userId = client.getCurrentSession().getUserId();
            CCheckInRequest packet = new CCheckInRequest(userId);

            client.request(packet, response -> {
                if (response instanceof SCheckInResponse checkInResponse) {
                    if (checkInResponse.isSuccess()) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this, "출근이 기록되었습니다.");
                            updateRecordList();
                        });
                    } else {
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(this, "출근 실패: " + checkInResponse.getMessage()));
                    }
                }
            });
        });

        // 퇴근 버튼 클릭 처리
        checkOutButton.addActionListener(e -> {
            String userId = client.getCurrentSession().getUserId();
            CCheckOutRequest packet = new CCheckOutRequest(userId);

            client.request(packet, response -> {
                if (response instanceof SCheckOutResponse checkOutResponse) {
                    if (checkOutResponse.isSuccess()) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this, "퇴근이 기록되었습니다.");
                            updateRecordList();
                        });
                    } else {
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(this, "퇴근 실패: " + checkOutResponse.getMessage()));
                    }
                }
            });
        });

        // 기록 수정 요청 화면 열기
        editRequestButton.addActionListener(e -> {
            new AttendanceEditScreen(this, client).setVisible(true);
        });

        bottomPanel.add(checkInButton);
        bottomPanel.add(checkOutButton);
        bottomPanel.add(editRequestButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 최초 로딩 시 기록 불러오기
        updateRecordList();
    }

    // 출퇴근 기록 목록을 서버에서 받아와서 갱신
    private void updateRecordList() {
        String userId = client.getCurrentSession().getUserId();
        client.request(new CGetAttendanceListRequest(userId), response -> {
            if (response instanceof SGetAttendanceListResponse res && res.isSuccess()) {
                List<Attendance> records = res.getRecords();
                if (records == null) records = List.of();

                final List<Attendance> finalRecords = records;

                SwingUtilities.invokeLater(() -> {
                    try {
                        recordListPanel.removeAll();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        if (finalRecords.isEmpty()) {
                            recordListPanel.add(new JLabel("출퇴근 기록이 없습니다."));
                        } else {
                            for (Attendance record : finalRecords) {
                                JPanel card = new JPanel(new BorderLayout(5, 5));
                                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                                card.setBackground(Color.WHITE);
                                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                                JPanel info = new JPanel(new GridLayout(2, 2, 5, 5));

                                String dateStr = record.getCheckInTime() != null ?
                                        record.getCheckInTime().toLocalDateTime().format(formatter) : "정보 없음";
                                String checkInStr = record.getCheckInTime() != null ?
                                        record.getCheckInTime().toLocalDateTime().format(formatter) : "";
                                String checkOutStr = record.getCheckOutTime() != null ?
                                        record.getCheckOutTime().toLocalDateTime().format(formatter) : "";

                                int totalMin = record.getWorkTimeTotal();
                                String workTime = (totalMin / 60 > 0 ? (totalMin / 60) + "시간 " : "") + (totalMin % 60) + "분";

                                info.add(new JLabel("날짜: " + dateStr));
                                info.add(new JLabel("총 근무: " + workTime));
                                info.add(new JLabel("출근: " + checkInStr));
                                info.add(new JLabel("퇴근: " + checkOutStr));

                                card.add(info, BorderLayout.CENTER);
                                recordListPanel.add(card);
                                recordListPanel.add(Box.createVerticalStrut(10));
                            }
                        }

                        recordListPanel.revalidate();
                        recordListPanel.repaint();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "기록 표시 오류: " + ex.getMessage());
                    }
                });

            } else {
                SwingUtilities.invokeLater(() -> {
                    String msg = (response instanceof SGetAttendanceListResponse res) ? res.getMessage() : "응답 없음";
                    JOptionPane.showMessageDialog(this, "조회 실패: " + msg);
                });
            }
        });
    }

}
