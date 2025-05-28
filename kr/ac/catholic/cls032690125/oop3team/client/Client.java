package kr.ac.catholic.cls032690125.oop3team.client;

import kr.ac.catholic.cls032690125.oop3team.ProgramProperties;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Thread thread;

    private List<ClientResponseListener> listeners = new ArrayList<ClientResponseListener>();
    private final ClientInteractor interactor = new ClientInteractor(this);

    private final ProgramProperties properties;

    public Client(ProgramProperties properties) {
        this.properties = properties;
        listeners.add(interactor);
    }

    public boolean connect() {
        try {
            socket = new Socket(properties.getClientTarget(), properties.getClientTargetPort());
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            thread = new Thread(this);
            thread.start();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void request(ClientOrderBasePacket packet, ClientInteractResponse callback) throws IOException {
        interactor.register(packet.getRequestId(), callback);
        send(packet);
    }

    public void send(ClientOrderBasePacket packet) throws IOException {
        out.writeObject(packet);
    }

    public void close() throws IOException {
        socket.close();
    }

    public ProgramProperties getProperties() {
        return properties;
    }

    public void dispatch(ServerResponseBasePacket packet) {
        for (ClientResponseListener listener : listeners) {
            listener.dispatch(packet);
        }
    }

    @Override
    public void run() {
        try{
            while(in != null) {
                Object obj = in.readObject();
                this.dispatch((ServerResponseBasePacket)obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
