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
                client.connect();
                break;
            case SERVER:
                Server server = new Server(properties);
                server.start();
                break;
        }
    }
} 