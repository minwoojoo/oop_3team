package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.serverside.AttendanceDAO;
import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.server.Server;

import javax.swing.*;
import java.awt.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class AttendanceEditScreen extends JFrame {
    private String userId;
    private Server server;
    private AttendanceDAO attendanceDAO;

    public AttendanceEditScreen(JFrame parent, Session session,Server server) {
        this.userId = session.getUserId();
        this.server = server;
        this.attendanceDAO = new AttendanceDAO(server);

        setTitle("출퇴근 기록 수정 요청");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 날짜 선택 패널
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("수정할 날짜: ");
//        JComboBox<String> dateComboBox = new JComboBox<>(new String[]{
//                "2024-03-20", "2024-03-19", "2024-03-18"
//        });
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
//        JTextField checkInField = new JTextField();
//        checkInField.setText("09:00");


        JLabel checkOutLabel = new JLabel("퇴근 시간 (HH:mm): ");
        JTextField checkOutField = new JTextField("18:00");
//        JTextField checkOutField = new JTextField();
//        checkOutField.setText("18:00");

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

        submitButton.addActionListener(e -> {
            Date selectedDate = (Date) dateSpinner.getValue();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
            String checkIn = checkInField.getText().trim();
            String checkOut = checkOutField.getText().trim();
            String reason = reasonArea.getText().trim();

            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(this, "수정 사유를 입력해주세요.",
                        "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (!attendanceDAO.hasAttendanceOnDate(userId, date)) {
                    JOptionPane.showMessageDialog(this,
                            "선택한 날짜에 출근 기록이 없습니다. 요청을 제출할 수 없습니다.",
                            "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                attendanceDAO.submitEditRequest(userId, date, checkIn, checkOut, reason);
                JOptionPane.showMessageDialog(this,
                        "수정 요청이 제출되었습니다.\n날짜: " + date + "\n출근: " + checkIn + "\n퇴근: " + checkOut,
                        "요청 완료", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "시간 형식이 잘못되었습니다. 예: 09:00",
                        "시간 오류", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "요청 저장 중 오류가 발생했습니다:\n" + ex.getMessage(),
                        "DB 오류", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}