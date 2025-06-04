package gui.attendance;

import javax.swing.*;
import java.awt.*;

//Add this import to import database
import DB.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddScheduleScreen extends JFrame {
    private JTextField titleField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextArea memoArea;

    public AddScheduleScreen(JFrame parent) {
        setTitle("일정 등록");
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 입력 필드 패널
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        // 제목 입력
        inputPanel.add(new JLabel("제목 *"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        // 날짜 선택
        inputPanel.add(new JLabel("날짜 *"));
        JPanel datePanel = new JPanel(new BorderLayout());
        dateField = new JTextField();
        JButton datePicker = new JButton("📅");
        datePicker.addActionListener(e -> {
            // 샘플 날짜 선택
            dateField.setText("2024-03-20");
        });
        datePanel.add(dateField, BorderLayout.CENTER);
        datePanel.add(datePicker, BorderLayout.EAST);
        inputPanel.add(datePanel);

        // 시간 선택
        inputPanel.add(new JLabel("시간 *"));
        JPanel timePanel = new JPanel(new BorderLayout());
        timeField = new JTextField();
        JButton timePicker = new JButton("⏰");
        timePicker.addActionListener(e -> {
            // 샘플 시간 선택
            timeField.setText("14:00");
        });
        timePanel.add(timeField, BorderLayout.CENTER);
        timePanel.add(timePicker, BorderLayout.EAST);
        inputPanel.add(timePanel);

        // 메모 입력
        inputPanel.add(new JLabel("메모"));
        memoArea = new JTextArea(3, 20);
        memoArea.setLineWrap(true);
        JScrollPane memoScroll = new JScrollPane(memoArea);
        inputPanel.add(memoScroll);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("등록");
        JButton cancelButton = new JButton("취소");

        saveButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();

            if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "필수 항목을 모두 입력해주세요",
                    "입력 오류",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

//            // 샘플 저장 메시지
//            JOptionPane.showMessageDialog(this,
//                "일정이 등록되었습니다.",
//                "일정 등록",
//                JOptionPane.INFORMATION_MESSAGE);
//            dispose();
            //replace with this to allow add schedule to Database
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO schedule (title, date, time, memo) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, title);
                    stmt.setString(2, date);
                    stmt.setString(3, time);
                    stmt.setString(4, memoArea.getText().trim());

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this,
                            "일정이 등록되었습니다.",
                            "일정 등록",
                            JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "일정 등록에 실패했습니다.",
                            "오류",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "데이터베이스 오류: " + ex.getMessage(),
                    "DB 오류",
                    JOptionPane.ERROR_MESSAGE);
            }

        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
} 