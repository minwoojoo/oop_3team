package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CGetAttendanceListRequest extends ClientOrderBasePacket {
//    private final String userId;
    private int chatroomId;

    public CGetAttendanceListRequest(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public int getChatroomId() {
        return chatroomId;
    }



}
