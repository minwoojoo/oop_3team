package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SChatroomListPacket extends ServerResponseBasePacket {
    private final Chatroom[] rooms;
    private final String errorMsg;

    public SChatroomListPacket(long requestId,Chatroom[] rooms, String errorMsg) {
        super(requestId);
        this.rooms    = rooms;
        this.errorMsg = errorMsg;
    }

    public Chatroom[] getRooms() {
        return rooms;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
