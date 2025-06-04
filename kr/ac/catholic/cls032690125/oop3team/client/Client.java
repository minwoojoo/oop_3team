package kr.ac.catholic.cls032690125.oop3team.client;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseListener;
import kr.ac.catholic.cls032690125.oop3team.features.auth.clientside.gui.LoginScreen;
import kr.ac.catholic.cls032690125.oop3team.features.chat.clientside.CChatReceiver;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 클라이언트 중앙 클래스
 */
public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Thread inThread;
    private Thread outThread;

    private List<ClientResponseListener> listeners = new ArrayList<ClientResponseListener>();
    /**
     * 서버한테서 패킷을 받아 처리할 리스너를 등록합니다.
     * @param listener 등록할 리스너 클래스
     */
    public void registerListener(ClientResponseListener listener) { listeners.add(listener); }

    private final ClientInteractor interactor = new ClientInteractor(this);
    private final CChatReceiver chatReceiver = new CChatReceiver(this);
    public CChatReceiver getChatReceiver() { return chatReceiver; }

    private BlockingQueue<ClientOrderBasePacket> sendQueue = new LinkedBlockingQueue<>();

    private final ProgramProperties properties;

    private Session currentSession = null;
    public Session getCurrentSession() { return currentSession; }
    public void updateSession(Session currentSession) { this.currentSession = currentSession; }

    private MainScreen mainScreen;

    public Client(ProgramProperties properties) {
        this.properties = properties;
        listeners.add(interactor);
        listeners.add(chatReceiver);
    }


    /**
     * 클라이언트를 시작합니다.
     *
     * @see SwingUtilities
     * @return GUI 실행을 위한 Runnable 함수. 시동 실패 시 null 반환
     */
    public Runnable start() {
        if(connect()) return () -> {
            new LoginScreen(this).setVisible(true);
        };
        else return null;
    }

    /**
     * 환경 변수로 지정된 서버로 소켓을 연결합니다.
     *
     * @return 실행 성공 여부
     * @apiNote GUI 실행 시 start()를 사용하십시오.
     */
    public boolean connect() {
        try {
            socket = new Socket(properties.getClientTarget(), properties.getClientTargetPort());
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            inThread = new Thread(this::runIn);
            inThread.start();
            outThread = new Thread(this::runOut);
            outThread.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 서버한테 패킷을 보내고 돌려받은 데이터를 처리해야 할 때 사용합니다.
     *
     * @param packet 서버한테 보낼 패킷
     * @param callback 서버한테 답장을 받으면 실행할 함수
     */
    public void request(ClientOrderBasePacket packet, ClientInteractResponse callback) {
        try{
            interactor.register(packet.getRequestId(), callback);
            send(packet);
        } catch (InterruptedException e) {
            callback.run(null);
        }
    }

    /**
     * 서버한테 패킷을 보냅니다.
     *
     * @param packet 서버한테 보낼 패킷
     * @throws InterruptedException 서버한테 패킷을 전송하는 데 실패했을 떄
     */
    public void send(ClientOrderBasePacket packet) throws InterruptedException {
        sendQueue.put(packet);
    }

    /**
     * 서버와의 연결을 끊습니다.
     *
     * @TODO 아직 덜 구현됨; GUI 닫고 알림을 보여주는 등 사용자 피드백 필요
     * @throws IOException
     */
    public void close() throws IOException {
        socket.close();
    }

    public ProgramProperties getProperties() {
        return properties;
    }

    /**
     * 서버한테서 패킷을 받았을 때 리스너를 실행하도록 하는 함수
     *
     * @apiNote 기능 구현 시 실행하지 마십시오.
     * @param packet 서버한테서 받은 패킷
     */
    public void dispatch(ServerResponseBasePacket packet) {
        for (ClientResponseListener listener : listeners) {
            listener.dispatch(packet);
        }
    }

    private void runIn() {
        try{
            while(in != null) {
                Object obj = in.readObject();
                this.dispatch((ServerResponseBasePacket)obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runOut() {
        try{
            while(out != null) {
                Object obj = sendQueue.take();
                out.writeObject(obj);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startMainScreen() {
        SwingUtilities.invokeLater(() -> {
            String userId = currentSession.getUserId();
            mainScreen = new MainScreen(userId, this);
            mainScreen.setVisible(true);
        });
    }


    @Deprecated
    public boolean connect(String a, int b) { return true; }

    @Deprecated
    public void send(String message) {
        //out.println(message);
    }
    @Deprecated
    public String receive() { return ""; }
}
