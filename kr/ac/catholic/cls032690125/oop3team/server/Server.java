package kr.ac.catholic.cls032690125.oop3team.server;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ProgramProperties properties;

    public Server(ProgramProperties control) {
        this.properties = control;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(properties.getServerPort())) {
            System.out.println("서버가 " + properties.getServerPort() + " 포트에서 시작되었습니다.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트 연결됨: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ProgramProperties getProperties() {
        return properties;
    }
}
