package kr.ac.catholic.cls032690125.oop3team.features.schedule.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CScheduleListPacket extends ClientOrderBasePacket {
    private int chatroomId;

    public CScheduleListPacket(int chatroomId) {
        super();
        this.chatroomId = chatroomId;
    }

    public int getChatroomId() { return chatroomId; }
}
