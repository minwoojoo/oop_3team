package common.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("서버가 " + port + " 포트에서 시작되었습니다.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트 연결됨: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 서버 실행용 main
    public static void main(String[] args) {
        int port = 12345; // 서버 포트 번호
        Server server = new Server(port);
        server.start();
    }
}
