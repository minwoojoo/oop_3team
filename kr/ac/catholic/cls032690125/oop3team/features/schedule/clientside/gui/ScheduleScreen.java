package kr.ac.catholic.cls032690125.oop3team.features.schedule.clientside.gui;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.features.schedule.clientside.CScheduleController;
import kr.ac.catholic.cls032690125.oop3team.models.Schedule;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleScreen extends JFrame {
    private List<Schedule> schedules = new ArrayList<>();
    private JPanel scheduleListPanel;

    private CScheduleController controller;
    private Client client;
    private CChatroomIndividualController chatcontroller;

    public ScheduleScreen(JFrame parent, Client client, CChatroomIndividualController chatcontroller) {
        this.client = client;
        this.chatcontroller = chatcontroller;
        controller = new CScheduleController(client);

        setTitle("일정 목록");
        setSize(400, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        // 샘플 일정 데이터 추가
//        schedules.add(new Schedule("주간 회의", "2024-03-20", "14:00", "주간 업무 진행 상황 공유"));
//        schedules.add(new Schedule("프로젝트 마감", "2024-03-25", "18:00", "최종 발표 자료 제출"));
//        schedules.add(new Schedule("팀 빌딩", "2024-03-22", "19:00", "저녁 식사"));
//
        //replace this to call data from database
        //schedules = DB.ScheduleDAO.getAllSchedules();  // ✅ DB에서 일정 불러오기

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 일정 목록 패널
        scheduleListPanel = new JPanel();
        scheduleListPanel.setLayout(new BoxLayout(scheduleListPanel, BoxLayout.Y_AXIS));
        //updateScheduleList();

        JScrollPane scrollPane = new JScrollPane(scheduleListPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void updateScheduleList() {
        scheduleListPanel.removeAll();

        for (Schedule schedule : schedules) {
            JPanel scheduleCard = new JPanel(new BorderLayout(5, 5));
            scheduleCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            scheduleCard.setBackground(Color.WHITE);
            scheduleCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            scheduleCard.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // 일정 정보 패널
            JPanel infoPanel = new JPanel(new BorderLayout(5, 5));

            JLabel titleLabel = new JLabel(schedule.getTitle());
            titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

            JLabel datetimeLabel = new JLabel(schedule.getDate() + " " + schedule.getTime());
            datetimeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));

            infoPanel.add(titleLabel, BorderLayout.NORTH);
            infoPanel.add(datetimeLabel, BorderLayout.SOUTH);

            scheduleCard.add(infoPanel, BorderLayout.CENTER);
            scheduleCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new ScheduleDetailScreen(schedule).setVisible(true);
                }
            });

            scheduleListPanel.add(scheduleCard);
            scheduleListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        scheduleListPanel.revalidate();
        scheduleListPanel.repaint();
    }

    @Override
    public void setVisible(boolean b) {
        initiate();
        super.setVisible(b);
    }

    private void initiate() {
        controller.requestSchedule(chatcontroller.getChatroom().getChatroomId(), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Schedule[]>>() {
            @Override
            protected void execute(ServerResponsePacketSimplefied<Schedule[]> data) {
                schedules = List.of(data.getData());
                updateScheduleList();
            }
        });
    }
}