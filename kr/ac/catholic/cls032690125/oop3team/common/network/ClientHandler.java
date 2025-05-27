package kr.ac.catholic.cls032690125.oop3team.common.network;

import java.io.*;
import java.net.Socket;
import kr.ac.catholic.cls032690125.oop3team.auth.controller.LoginController;
import kr.ac.catholic.cls032690125.oop3team.auth.controller.SignupController;
import kr.ac.catholic.cls032690125.oop3team.auth.controller.SessionController;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split("\\|");
                String command = parts[0];
                if ("LOGIN".equalsIgnoreCase(command)) {
                    String userId = parts[1];
                    String password = parts[2];
                    LoginController loginController = new LoginController();
                    boolean result = loginController.login(userId, password);
                    if (result) {
                        // 세션 생성
                        SessionController.createSession(userId);
                    }
                    out.println(result ? "SUCCESS" : "FAIL");
                } else if ("SIGNUP".equalsIgnoreCase(command)) {
                    String userId = parts[1];
                    String name = parts[2];
                    String password = parts[3];
                    SignupController signupController = new SignupController();
                    boolean result = signupController.signup(userId, name, password);
                    out.println(result ? "SUCCESS" : "FAIL");
                } else if ("SESSIONCHECK".equalsIgnoreCase(command)) {
                    String userId = parts[1];
                    boolean active = SessionController.isSessionActive(userId);
                    out.println(active ? "ACTIVE" : "EXPIRED");
                } else if ("IDCHECK".equalsIgnoreCase(command)) {
                    String userId = parts[1];
                    SignupController signupController = new SignupController();
                    boolean duplicate = signupController.isIdDuplicate(userId);
                    out.println(duplicate ? "DUPLICATE" : "AVAILABLE");
                } else if ("LOGOUT".equalsIgnoreCase(command)) {
                    String userId = parts[1];
                    // 세션 삭제
                    SessionController.removeSession(userId);
                    out.println("LOGOUT_SUCCESS");
                } else {
                    out.println("UNKNOWN_COMMAND");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}