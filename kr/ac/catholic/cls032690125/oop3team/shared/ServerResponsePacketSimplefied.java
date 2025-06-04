package kr.ac.catholic.cls032690125.oop3team.shared;

import java.io.Serializable;

/**
 * 클라이언트에게 간단한 답장을 보낼 때 사용하는 클래스
 *
 * @param <T> 보낼 데이터의 타입
 */
public class ServerResponsePacketSimplefied<T extends Serializable> extends ServerResponseBasePacket {
    private T data;

    /**
     * @param data 보낼 데이터
     * @apiNote 잠시만요! 이건 브로드캐스팅용 패킷을 만들때 씁니다! 클라이언트의 요청을 처리할 떄는 ServerResponsePacketSimplefied(long, T)을 사용하세요!
     */
    @Deprecated
    public ServerResponsePacketSimplefied(T data) {
        super();
        this.data = data;
    }

    /**
     * @param requestID 클라이언트한테서 받았던 패킷의 id
     * @param data 보낼 데이터
     */
    public ServerResponsePacketSimplefied(long requestID, T data) {
        super(requestID);
        this.data = data;
    }

    public T getData() { return data; }
}
