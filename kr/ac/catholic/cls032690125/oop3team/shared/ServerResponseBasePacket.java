package kr.ac.catholic.cls032690125.oop3team.shared;

import java.io.Serializable;

public abstract class ServerResponseBasePacket implements Serializable {
    private static final long serialVersionUID = 45772000L;

    private long requestId; // -1일 경우 메시지 등 broadcast

    public ServerResponseBasePacket() {
        requestId = -1;
    }

    public ServerResponseBasePacket(long requestId) {
        this.requestId = requestId;
    }

    public long getRequestId() { return requestId; }
}
