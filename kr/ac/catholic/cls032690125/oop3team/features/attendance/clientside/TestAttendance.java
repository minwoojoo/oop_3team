//package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside;
//
//import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;
//import kr.ac.catholic.cls032690125.oop3team.client.Client;
//import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.GroupChatScreen;
//import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
//import kr.ac.catholic.cls032690125.oop3team.models.Session;
//import kr.ac.catholic.cls032690125.oop3team.server.Server;
//
//import javax.swing.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class TestAttendance {
//    public static void main(String[] args) {
//        System.out.println("ENV MODE = " + System.getenv("CLS032690125oop3team_MODE"));
//
//        // create session test
//        Session session = new Session();
//        session.setUserId("tuyen"); // \ user_id 'tuyen' in DB
//        session.setCreatedAt(LocalDateTime.now());
//
//
//        try {
//
//            Map<String, String> envs = System.getenv();
//
//            ProgramProperties properties = new ProgramProperties(envs);
//
//            Server server = new Server(properties);
//            Client client = new Client(properties);
//            client.setCurrentSession(session);
//
//            Chatroom chatroom = new Chatroom();
//            chatroom.setChatroomId(1);
//            chatroom.setTitle("aaa");
//
//            List<String> members = new ArrayList<>();
//            members.add("tuyen");
//            members.add("user1");
//            members.add("user2");
//
//
//            //  AttendanceScreen
//            SwingUtilities.invokeLater(() -> {
//                try {
//                    new GroupChatScreen(chatroom.getTitle(), members,client, server,chatroom).setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
