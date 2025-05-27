import gui.auth.LoginScreen;
import common.network.Client;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        if (!client.connect("localhost", 12345)) {
            JOptionPane.showMessageDialog(null, "서버 연결 실패!");
            return;
        }
        new LoginScreen(client).setVisible(true);
    }
} 