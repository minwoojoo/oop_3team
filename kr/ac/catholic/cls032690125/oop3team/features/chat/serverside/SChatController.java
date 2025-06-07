package kr.ac.catholic.cls032690125.oop3team.features.chat.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.CMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.CMessageSendPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageBroadcastPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import java.util.ArrayList;
import java.util.List;

public class SChatController extends ServerRequestListener {
    private ChatDAO chatDAO;

    public SChatController(Server client) {
        super(client);
        chatDAO = new ChatDAO(client);
    }

    @ServerRequestHandler(CMessageSendPacket.class)
    public void sendMessage(ServerClientHandler sch, CMessageSendPacket packet) {
        try{
            // insert
            var msg = packet.getMessage();
            broadcastMessage(msg);
            sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), true));
        } catch (Exception e) {
            e.printStackTrace();
            sch.send(new ServerResponsePacketSimplefied<>(packet.getRequestId(), false));
        }
    }

    @ServerRequestHandler(CMessageLoadPacket.class)
    public void loadMessage(ServerClientHandler sch, CMessageLoadPacket packet) {
        try{
            System.out.println("TEST");
            sch.send(
                    new SMessageLoadPacket(
                            packet.getRequestId(),
                            chatDAO.loadMessages(packet.getChatroomId(), packet.getRefPoint(), packet.getPeriod())
                    )
            );
            System.out.println("TES2");
        } catch (Exception e) {
            e.printStackTrace();
            sch.send(new SMessageLoadPacket(packet.getRequestId(), null));
        }
    }

    public void broadcastMessage(Message msg) throws Exception {
        // insert
        long newid = chatDAO.insertMessage(msg);
        if(newid == -1) throw new Exception("DB FAILURE");
        msg = new Message(newid, msg.getChatroomId(), msg.getSenderId(), msg.getContent(), msg.isSystem(), msg.getSent());
        server.broadcast(
                new SMessageBroadcastPacket(msg),
                server.getChatroomController().getMemberList(msg.getChatroomId())
        );
    }
}
