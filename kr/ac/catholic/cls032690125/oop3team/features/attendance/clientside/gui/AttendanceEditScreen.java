package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.shared.*;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceEditScreen extends JFrame {
    private final String userId;
    private final Client client;

    public AttendanceEditScreen(JFrame parent, Client client) {
        this.client = client;
        this.userId = client.getCurrentSession().getUserId();

        setTitle("출퇴근 기록 수정 요청");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 날짜 선택 패널
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("수정할 날짜: ");
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        datePanel.add(dateLabel);
        datePanel.add(dateSpinner);
        mainPanel.add(datePanel, BorderLayout.NORTH);

        // 수정 내용 패널
        JPanel editPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel checkInLabel = new JLabel("출근 시간 (HH:mm): ");
        JTextField checkInField = new JTextField("09:00");

        JLabel checkOutLabel = new JLabel("퇴근 시간 (HH:mm): ");
        JTextField checkOutField = new JTextField("18:00");

        JLabel reasonLabel = new JLabel("수정 사유: ");
        JTextArea reasonArea = new JTextArea(3, 20);
        reasonArea.setLineWrap(true);
        JScrollPane reasonScroll = new JScrollPane(reasonArea);

        editPanel.add(checkInLabel);
        editPanel.add(checkInField);
        editPanel.add(checkOutLabel);
        editPanel.add(checkOutField);
        editPanel.add(reasonLabel);
        editPanel.add(reasonScroll);

        mainPanel.add(editPanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("요청 제출");
        JButton cancelButton = new JButton("취소");

        // 제출 버튼 이벤트 처리
        submitButton.addActionListener(e -> {
            Date selectedDate = (Date) dateSpinner.getValue();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
            String checkIn = checkInField.getText().trim();
            String checkOut = checkOutField.getText().trim();
            String reason = reasonArea.getText().trim();

            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "수정 사유를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isValidTimeFormat(checkIn) || !isValidTimeFormat(checkOut)) {
                JOptionPane.showMessageDialog(this, "시간 형식이 올바르지 않습니다. (예: 09:00)", "시간 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 1. 서버에 해당 날짜의 출퇴근 기록이 존재하는지 확인
            client.request(new CCheckIfAlreadyCheckedInRequest(userId, date), response -> {
                if (response instanceof SCheckIfAlreadyCheckedInResponse res) {
                    SwingUtilities.invokeLater(() -> {
                        if (!res.isSuccess()) {
                            JOptionPane.showMessageDialog(this, "서버 오류: 출근 기록 확인 실패", "오류", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!res.isAlreadyCheckedIn()) {
                            JOptionPane.showMessageDialog(this, "해당 날짜에 출퇴근 기록이 없습니다. 수정 요청 불가.", "오류", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // 2. 요청 생성 및 전송
                        long requestId = System.currentTimeMillis();
                        CSubmitEditAttendanceRequest packet = new CSubmitEditAttendanceRequest(
                                requestId, userId, date, checkIn, checkOut, reason
                        );

                        client.request(packet, r -> {
                            if (r instanceof SSubmitEditAttendanceResponse editRes) {
                                SwingUtilities.invokeLater(() -> {
                                    if (editRes.isSuccess()) {
                                        JOptionPane.showMessageDialog(this,
                                                "수정 요청이 제출되었습니다.\n날짜: " + date +
                                                        "\n출근: " + checkIn + "\n퇴근: " + checkOut,
                                                "요청 완료",
                                                JOptionPane.INFORMATION_MESSAGE);
                                        dispose();
                                    } else {
                                        JOptionPane.showMessageDialog(this, "요청 실패: " + editRes.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                                    }
                                });
                            }
                        });
                    });
                }
            });
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private boolean isValidTimeFormat(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sdf.setLenient(false);
            sdf.parse(time);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
