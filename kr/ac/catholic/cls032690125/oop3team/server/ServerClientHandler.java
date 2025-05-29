package kr.ac.catholic.cls032690125.oop3team.server;

import kr.ac.catholic.cls032690125.oop3team.features.models.Session;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.io.*;
import java.net.Socket;

//TODO: ADD SESSION
public class ServerClientHandler implements Runnable {
    private final Server server;
    private final Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private boolean running = true;

    public ServerClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public Session getSession() {
        //TODO ADD IT
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void send(ServerResponseBasePacket response) {
        if (!running) return;
        try{
            synchronized (out) {
                out.writeObject(response);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (running) {
                ClientOrderBasePacket response = (ClientOrderBasePacket) in.readObject();

                server.handle(this, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            running = false;
        }
    }
}
