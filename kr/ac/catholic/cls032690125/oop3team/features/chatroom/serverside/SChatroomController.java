package kr.ac.catholic.cls032690125.oop3team.features.chatroom.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.CChatroomListLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;

public class SChatroomController extends ServerRequestListener {
    public SChatroomController(Server client) {
        super(client);
    }

    @ServerRequestHandler(CChatroomListLoadPacket.class)
    public void loadChatroomList(ServerClientHandler sch, CChatroomListLoadPacket packet) {
        //TODO: IMPL IT
    }

    /**
     * 특정 채팅방의 맴버 목록을 불러옵니다.
     *
     * @param chatroomId 채팅방 id
     * @return 채팅방 맴버의 user id
     */
    public String[] getMemberList(int chatroomId) {
        //TODO: IMPL IT
        return null;
    }
}
