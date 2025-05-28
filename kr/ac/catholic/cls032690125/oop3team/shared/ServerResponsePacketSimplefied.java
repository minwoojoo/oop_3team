package kr.ac.catholic.cls032690125.oop3team.shared;

import java.io.Serializable;

public class ServerResponsePacketSimplefied<T extends Serializable> extends ServerResponseBasePacket {
    private T data;

    public ServerResponsePacketSimplefied(T data) {
        super();
        this.data = data;
    }

    public ServerResponsePacketSimplefied(long requestID, T data) {
        super(requestID);
        this.data = data;
    }

    public T getData() { return data; }
}
