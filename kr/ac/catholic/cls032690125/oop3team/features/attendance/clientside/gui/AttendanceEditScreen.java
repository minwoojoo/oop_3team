package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.shared.CSubmitEditAttendanceRequest;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.shared.SSubmitEditAttendanceResponse;

import javax.swing.*;
import java.awt.*;
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

        // 제출 버튼 클릭 이벤트 처리
        submitButton.addActionListener(e -> {
            // 선택한 날짜, 출근 시간, 퇴근 시간, 사유 읽기
            Date selectedDate = (Date) dateSpinner.getValue();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
            String checkIn = checkInField.getText().trim();
            String checkOut = checkOutField.getText().trim();
            String reason = reasonArea.getText().trim();

            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "수정 사유를 입력해주세요.",
                        "입력 오류",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 생성 요청 ID (유니크하게)
            long requestId = System.currentTimeMillis();

            // 서버에 제출 요청 패킷 생성
            CSubmitEditAttendanceRequest packet = new CSubmitEditAttendanceRequest(
                    requestId, userId, date, checkIn, checkOut, reason);

            // client.request로 서버에 요청 전송 및 응답 처리
            client.request(packet, response -> {
                if (response instanceof SSubmitEditAttendanceResponse editResponse) {
                    SwingUtilities.invokeLater(() -> {
                        if (editResponse.isSuccess()) {
                            JOptionPane.showMessageDialog(this,
                                    "수정 요청이 제출되었습니다.\n날짜: " + date +
                                            "\n출근: " + checkIn +
                                            "\n퇴근: " + checkOut,
                                    "요청 완료",
                                    JOptionPane.INFORMATION_MESSAGE);
                            dispose(); // 창 닫기
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "요청 실패: " + editResponse.getMessage(),
                                    "오류",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }
            });
        });

        // 취소 버튼 - 창 닫기
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
