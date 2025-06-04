package kr.ac.catholic.cls032690125.oop3team.shared;

import java.io.Serializable;
import java.util.UUID;

public abstract class ClientOrderBasePacket implements Serializable {
    private static final long serialVersionUID = 45771000L;

    private long requestId; // timecode + 랜덤 숫자

    public ClientOrderBasePacket() {
        requestId = UUID.randomUUID().getLeastSignificantBits();
    }

    public ClientOrderBasePacket(long requestId) {
        this.requestId = requestId;
    }

    public long getRequestId() { return requestId; }
}
