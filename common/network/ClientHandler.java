package common.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final CopyOnWriteArrayList<PrintWriter> clients;

    public ClientHandler(Socket socket, CopyOnWriteArrayList<PrintWriter> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            clients.add(out);

            String msg;

            while ((msg = in.readLine()) != null) {
                for (PrintWriter clientOut : clients) {
                    if (clientOut != out) {
                        clientOut.println(msg);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("연결 해제 되었음.");
        }finally {
            try {
                if (out!=null) clients.remove(out);
                if (socket!=null) socket.close();
            }catch (IOException ignored){}
        }
    }
}