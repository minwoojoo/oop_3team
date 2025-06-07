package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CChatroomMemberListPacket extends ClientOrderBasePacket {
    private int chatroomId;

    public CChatroomMemberListPacket(int chatroomId) {
        super();
        this.chatroomId = chatroomId;
    }

    public int getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }
}
