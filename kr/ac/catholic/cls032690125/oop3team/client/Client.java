package kr.ac.catholic.cls032690125.oop3team.client;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private final ProgramProperties properties;

    public Client(ProgramProperties properties) {
        this.properties = properties;
    }

    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void send(String message) {
        out.println(message);
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }

    // 로그인 요청
    public boolean login(String userId, String password) throws IOException {
        send("LOGIN|" + userId + "|" + password);
        String response = receive();
        return "SUCCESS".equals(response);
    }

    // 회원가입 요청
    public boolean signup(String userId, String name, String password) throws IOException {
        send("SIGNUP|" + userId + "|" + name + "|" + password);
        String response = receive();
        return "SUCCESS".equals(response);
    }

    public boolean isIdDuplicate(String userId) throws IOException {
        send("IDCHECK|" + userId);
        String response = receive();
        return "DUPLICATE".equals(response);
    }

    public ProgramProperties getProperties() {
        return properties;
    }
}
