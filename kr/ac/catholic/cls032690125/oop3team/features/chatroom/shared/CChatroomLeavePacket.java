package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;


public class CChatroomLeavePacket extends ClientOrderBasePacket {
    private int chatroomId;
    private String userId;

    public CChatroomLeavePacket(int chatroomId, String userId) {
        this.chatroomId = chatroomId;
        this.userId = userId;
    }

    public int getChatroomId() { return chatroomId; }
    public String getUserId() { return userId; }
} 