package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;
import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.gui.AttendanceScreen;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.server.Server;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Map;

public class TestAttendanceScreen {
    public static void main(String[] args) {
        System.out.println("ENV MODE = " + System.getenv("CLS032690125oop3team_MODE"));

        // create session test
        Session session = new Session();
        session.setUserId("tuyen"); // \ user_id 'tuyen' in DB
        session.setCreatedAt(LocalDateTime.now());


        try {

            Map<String, String> envs = System.getenv();

            ProgramProperties properties = new ProgramProperties(envs);

            Server server = new Server(properties);
            Client client = new Client(properties);
            client.setCurrentSession(session);


            //  AttendanceScreen
            SwingUtilities.invokeLater(() -> {
                try {
                    new AttendanceScreen(null, client, server).setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
