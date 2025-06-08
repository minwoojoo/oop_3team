package kr.ac.catholic.cls032690125.oop3team.features.memo.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;
import java.io.Serializable;

public class SMemoUpdateResponsePacket extends ServerResponseBasePacket implements Serializable {
    private boolean success;
    private String message;

    public SMemoUpdateResponsePacket(long requestId, boolean success, String message) {
        super(requestId);
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
