package kr.ac.catholic.cls032690125.oop3team;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.server.Server;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ProgramProperties properties = new ProgramProperties(System.getenv());

        switch(properties.getMode()) {
            // 환경변수로 지정된 프로그램 동작 모드가 클라이언트인 경우
            case CLIENT:
                Client client = new Client(properties);
                Runnable guirunner = client.start();
                if(guirunner != null) // 서버 연결 등 프로그램 시동 성공 시
                    SwingUtilities.invokeLater(guirunner);
                else // 프로그램 시동 실패 시
                    System.out.println("프로그램 시동 실패");
                break;
            // 동작 모드가 서버인 경우
            case SERVER:
                Server server = new Server(properties);
                server.start();
                break;
        }
    }
} 