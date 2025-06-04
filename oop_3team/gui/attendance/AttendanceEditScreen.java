package gui.attendance;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceEditScreen extends JFrame {
    public AttendanceEditScreen(JFrame parent) {
        setTitle("출퇴근 기록 수정 요청");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 날짜 선택 패널
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("수정할 날짜: ");
        JComboBox<String> dateComboBox = new JComboBox<>(new String[]{
            "2024-03-20", "2024-03-19", "2024-03-18"
        });
        datePanel.add(dateLabel);
        datePanel.add(dateComboBox);
        mainPanel.add(datePanel, BorderLayout.NORTH);

        // 수정 내용 패널
        JPanel editPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        JLabel checkInLabel = new JLabel("출근 시간: ");
        JTextField checkInField = new JTextField();
        checkInField.setText("09:00");
        
        JLabel checkOutLabel = new JLabel("퇴근 시간: ");
        JTextField checkOutField = new JTextField();
        checkOutField.setText("18:00");
        
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
            String date = (String) dateComboBox.getSelectedItem();
            String checkIn = checkInField.getText();
            String checkOut = checkOutField.getText();
            String reason = reasonArea.getText();

            if (reason.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "수정 사유를 입력해주세요.", 
                    "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 요청 제출 처리
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String requestTime = sdf.format(new Date());
            
            JOptionPane.showMessageDialog(this, 
                "수정 요청이 제출되었습니다.\n" +
                "날짜: " + date + "\n" +
                "출근: " + checkIn + "\n" +
                "퇴근: " + checkOut + "\n" +
                "요청 시간: " + requestTime,
                "요청 완료", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
} 