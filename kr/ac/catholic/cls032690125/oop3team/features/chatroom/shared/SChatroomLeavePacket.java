package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;


public class SChatroomLeavePacket extends ServerResponseBasePacket {
    private boolean success;
    private String message;

    public SChatroomLeavePacket(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
} 