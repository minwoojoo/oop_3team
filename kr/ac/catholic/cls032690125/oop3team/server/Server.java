package kr.ac.catholic.cls032690125.oop3team.server;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;
import kr.ac.catholic.cls032690125.oop3team.exceptions.runtime.ServerIgnitionFailureException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ProgramProperties properties;
    private final Database database;

    public Server(ProgramProperties control) throws ServerIgnitionFailureException {
        try{
            this.properties = control;
            this.database = new Database(this);
        } catch (ClassNotFoundException e) {
            throw new ServerIgnitionFailureException("Not found database driver", e);
        }
    }

    public void start() throws ServerIgnitionFailureException {
        try (ServerSocket serverSocket = new ServerSocket(properties.getServerPort())) {
            System.out.println("서버가 " + properties.getServerPort() + " 포트에서 시작되었습니다.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트 연결됨: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            throw new ServerIgnitionFailureException("Unknown IO Exception", e);
        }
    }

    public ProgramProperties getProperties() {
        return properties;
    }

    public Database getDatabase() {
        return database;
    }
}
