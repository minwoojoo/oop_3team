package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SFriendInviteRes extends ServerResponseBasePacket implements java.io.Serializable {
    private boolean success;
    private String message;

    public SFriendInviteRes(long requestId, boolean success, String message) {
        super(requestId);
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
} 