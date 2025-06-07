package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SChatroomThreadClosePacket extends ServerResponseBasePacket {
    private final boolean success;
    private final String errorMessage;

    public SChatroomThreadClosePacket(long requestId, boolean success, String errorMessage) {
        super(requestId);
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

