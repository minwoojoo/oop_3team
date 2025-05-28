package kr.ac.catholic.cls032690125.oop3team;

import kr.ac.catholic.cls032690125.oop3team.client.gui.auth.LoginScreen;
import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.auth.shared.ClientLoginRequest;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ProgramProperties properties = new ProgramProperties(System.getenv());

        switch(properties.getMode()) {
            case CLIENT:
                Client client = new Client(properties);
                //if (!client.connect("localhost", 12345)) {
                //    JOptionPane.showMessageDialog(null, "서버 연결 실패!");
                //    return;
                //}
                //new LoginScreen(client).setVisible(true);
                try {
                    System.out.println("CONN:" + client.connect());

                    client.request(new ClientLoginRequest("hello", "hi"), (res) -> {
                        System.out.println("HELLO");
                        System.out.println(((ServerResponsePacketSimplefied<Boolean>) res).getData());
                    });
                } catch (Exception e) { e.printStackTrace(); }
                break;
            case SERVER:
                Server server = new Server(properties);
                server.start();
                break;
        }
    }
} 