package kr.ac.catholic.cls032690125.oop3team.server;

import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 서버의 클라이언트 연결 클래스
 */
public class ServerClientHandler {
    private final Server server;
    private final Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Thread inThread;
    private Thread outThread;

    private BlockingQueue<ServerResponseBasePacket> sendQueue = new LinkedBlockingQueue<>();

    private boolean running = true;

    public ServerClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    private Session session = null;
    public Session getSession() { return session; }
    public void updateSession(Session session) { this.session = session; }

    /**
     * 클라이언트에게 패킷 보내기
     *
     * @param response 클라이언트에게 보낼 패킷
     */
    public void send(ServerResponseBasePacket response) {
        if (!running) return;
        try{
            sendQueue.put(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 클라이언트 연결 시작
     *
     * @apiNote 기능 구현에 실행하지 마세요
     */
    public void start() {
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            this.inThread = new Thread(() -> {
                try{
                    while (running) {
                        ClientOrderBasePacket response = (ClientOrderBasePacket) in.readObject();
                        server.dispatch(this, response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    running = false;
                }
            });

            this.outThread = new Thread(() -> {
                try{
                    while (running) {
                        Object obj = sendQueue.take();
                        out.writeObject(obj);
                        out.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    running = false;
                }
            });

            this.inThread.start();
            this.outThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
