package kr.ac.catholic.cls032690125.oop3team.server;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;
import kr.ac.catholic.cls032690125.oop3team.exceptions.runtime.ServerIgnitionFailureException;
import kr.ac.catholic.cls032690125.oop3team.features.auth.serverside.ServerAuthController;
import kr.ac.catholic.cls032690125.oop3team.features.friend.serverside.SFriendController;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 서버 중앙 클래스
 */
public class Server {
    private final ProgramProperties properties;
    private final Database database;

    private final List<ServerClientHandler> onlineClients = new ArrayList<>();
    private final List<ServerRequestListener> listeners = new ArrayList<>();

    private ServerAuthController authController;

    /**
     * @param control 프로그램 실행 환경 변수
     * @throws ServerIgnitionFailureException 데이터베이스 드라이버가 유효하지 않을 때
     * @implSpec 서버 클래스가 생성될 때 데이터베이스 연결 클래스를 함께 생성하고 이는 데이터베이스 드라이버를 설정합니다.
     */
    public Server(ProgramProperties control) throws ServerIgnitionFailureException {
        try{
            this.properties = control;
            this.database = new Database(this);

            authController = new ServerAuthController(this);
            listeners.add(authController);
        } catch (ClassNotFoundException e) {
            throw new ServerIgnitionFailureException("Not found database driver", e);
        }
    }

    /**
     * 서버를 시작합니다.
     *
     * @throws ServerIgnitionFailureException 서버가 예기치 않게 종료될 때
     */
    public void start() throws ServerIgnitionFailureException {
        try (ServerSocket serverSocket = new ServerSocket(properties.getServerPort())) {
            System.out.println("서버가 " + properties.getServerPort() + " 포트에서 시작되었습니다.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트 연결됨: " + clientSocket.getInetAddress());
                var handler = new ServerClientHandler(this, clientSocket);
                handler.start();
                onlineClients.add(handler);
            }
        } catch (IOException e) {
            throw new ServerIgnitionFailureException("Unknown IO Exception", e);
        }
    }

    /**
     * 클라이언트한테서 패킷을 받았을 때 리스너를 실행하도록 하는 함수
     *
     * @apiNote 기능 구현 시 실행하지 마십시오.
     * @param handler 패킷을 보낸 클라이언트
     * @param order 클라이언트 측에서 받은 패킷
     */
    public void dispatch(ServerClientHandler handler, ClientOrderBasePacket order) {
        for(ServerRequestListener listener : listeners) {
            listener.dispatch(handler, order);
        }
    }

    public ProgramProperties getProperties() {
        return properties;
    }

    public Database getDatabase() {
        return database;
    }
}
