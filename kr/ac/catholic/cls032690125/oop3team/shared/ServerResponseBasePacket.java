package kr.ac.catholic.cls032690125.oop3team.shared;


import java.io.Serializable;

public abstract class ServerResponseBasePacket implements Serializable {
    private static final long serialVersionUID = 45772000L;

    private long requestId; // -1일 경우 메시지 등 broadcast

    /**
     * @apiNote 잠시만요! 이건 브로드캐스팅용 패킷을 만들때 씁니다! 클라이언트의 요청을 처리할 떄는 super(long) 함수를 사용하세요!
     */
    public ServerResponseBasePacket() {
        requestId = -1;
    }

    /**
     * @param requestId 클라이언트한테서 받았던 패킷의 requestId
     */
    public ServerResponseBasePacket(long requestId) {
        this.requestId = requestId;
    }

    public long getRequestId() {
        return requestId;
    }
}
