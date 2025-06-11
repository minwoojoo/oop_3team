package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.AttendanceEditRequest;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class AttendanceEditRequestManageScreen extends JFrame {
    private JPanel listPanel;
    private Client client;

    public AttendanceEditRequestManageScreen(JFrame parent, Client client) {
        this.client = client;

        setTitle("근태 수정 요청 관리");
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listPanel);
        add(scrollPane);

        loadRequests();
    }

    public void loadRequests() {
        String userId = client.getCurrentSession().getUserId();
        System.out.println("[DEBUG] Sending request with userId = " + userId);
        long requestId = System.currentTimeMillis();
        CGetAttendanceEditRequestList paket = new CGetAttendanceEditRequestList(userId);

        client.request(paket, response -> {
            System.out.println("[DEBUG] Response received: " + response);
            if (response instanceof SGetAttendanceEditRequestList res && res.isSuccess()) {
                List<AttendanceEditRequest> list = res.getRequests();
                System.out.println("[DEBUG] Number of edit requests = " + (list != null ? list.size() : "null"));
                SwingUtilities.invokeLater(() -> {
                    assert list != null;
                    showRequests(list);
                });
            } else {
                JOptionPane.showMessageDialog(this, "요청 목록 불러오기 실패");
            }
        });
    }



    private void showRequests(List<AttendanceEditRequest> requests) {
        listPanel.removeAll();

        for (AttendanceEditRequest req : requests) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Request id=" + req.getId() + ", userId=" + req.getUserId() +
                    ", date=" + req.getAttendanceDate() +
                    ", checkIn=" + req.getRequestedCheckIn() +
                    ", checkOut=" + req.getRequestedCheckOut() +
                    ", reason=" + req.getReason() +
                    ", status=" + req.getStatus());

            JPanel card = new JPanel(new BorderLayout(5, 5));
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            card.setBackground(Color.WHITE);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            String formattedDate = dateTimeFormat.format(req.getAttendanceDate());
            String info = String.format(
                    "<html><b>유저:</b> %s<br><b>날짜:</b> %s<br><b>출근:</b> %s<br><b>퇴근:</b> %s<br><b>사유:</b> %s<br><b>상태:</b> %s</html>",
                    req.getUserId(), formattedDate, req.getRequestedCheckIn(),
                    req.getRequestedCheckOut(), req.getReason(), req.getStatus()
            );

            JLabel label = new JLabel(info);
            card.add(label, BorderLayout.CENTER);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton approveBtn = new JButton("승인");
            JButton rejectBtn = new JButton("거절");

            approveBtn.addActionListener(e -> {
                handleDecision(req.getId(), true, approveBtn, rejectBtn);
            });

            rejectBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(this, "정말로 거절하시겠습니까?", "거절 확인", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    handleDecision(req.getId(), false, approveBtn, rejectBtn);
                }
            });

            btnPanel.add(approveBtn);
            btnPanel.add(rejectBtn);
            card.add(btnPanel, BorderLayout.SOUTH);

            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private void handleDecision(long editRequestId, boolean approved, JButton approveBtn, JButton rejectBtn) {
        long requestId = System.currentTimeMillis();

        CApproveEditAttendanceRequest packet = new CApproveEditAttendanceRequest(
                requestId, editRequestId, approved
        );

        client.request(packet, response -> {
            if (response instanceof SApproveEditAttendanceResponse res && res.isSuccess()) {
                SwingUtilities.invokeLater(() -> {
                    if (approved) {
                        approveBtn.setBackground(Color.GREEN);
                        approveBtn.setForeground(Color.WHITE);
                    } else {
                        rejectBtn.setBackground(Color.RED);
                        rejectBtn.setForeground(Color.WHITE);
                    }

                    approveBtn.setEnabled(false);
                    rejectBtn.setEnabled(false);

                    JOptionPane.showMessageDialog(this, "처리 완료");
                });
            } else {
                JOptionPane.showMessageDialog(this, "처리 실패: " + ((SApproveEditAttendanceResponse) response).getMessage());
            }
        });
    }
}