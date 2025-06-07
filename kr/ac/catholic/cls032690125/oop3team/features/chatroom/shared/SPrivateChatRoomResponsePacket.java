package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SPrivateChatRoomResponsePacket extends ServerResponseBasePacket {
    private Chatroom chatroom;
    public SPrivateChatRoomResponsePacket(long requestId,Chatroom chatroom) {
        super(requestId);
        this.chatroom = chatroom;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }
}
