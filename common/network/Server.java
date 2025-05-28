package common.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final int PORT = 12345;
    private static final CopyOnWriteArrayList<PrintWriter> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("<!==서버 시작==!>");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("클라이언트 접속: " + socket);
            new Thread(new ClientHandler(socket, clients)).start();
        }
    }
}