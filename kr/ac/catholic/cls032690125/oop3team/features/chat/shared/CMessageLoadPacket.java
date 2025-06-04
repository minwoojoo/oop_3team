package kr.ac.catholic.cls032690125.oop3team.features.chat.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CMessageLoadPacket extends ClientOrderBasePacket {
    private int chatroomId;
    // 메시지 아이디, 0이면 기준점 맨 마지막/맨 처음
    private long refPoint;
    // -면 위(기준 채팅 이전)로, +면 아래(기준 채팅 이후)로
    private int period;

    public CMessageLoadPacket(int chatroomId, long refPoint, int period) {
        super();
        this.chatroomId = chatroomId;
        this.refPoint = refPoint;
        this.period = period;
    }

    public int getChatroomId() { return chatroomId; }
    public long getRefPoint() { return refPoint; }
    public int getPeriod() { return period; }
}
