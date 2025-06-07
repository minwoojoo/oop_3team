package kr.ac.catholic.cls032690125.oop3team.features.schedule.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.models.Schedule;

import javax.swing.*;
import java.awt.*;

public class ScheduleDetailScreen extends JFrame {
    public ScheduleDetailScreen(Schedule schedule) {
        setTitle("일정 상세 정보");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 일정 정보 표시
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));

        JLabel titleLabel = new JLabel("제목: " + schedule.getTitle());
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        JLabel dateLabel = new JLabel("날짜: " + schedule.getDate());
        dateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        JLabel timeLabel = new JLabel("시간: " + schedule.getTime());
        timeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        JLabel memoLabel = new JLabel("메모: " + schedule.getMemo());
        memoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

        infoPanel.add(titleLabel);
        infoPanel.add(dateLabel);
        infoPanel.add(timeLabel);
        infoPanel.add(memoLabel);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // 닫기 버튼
        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}