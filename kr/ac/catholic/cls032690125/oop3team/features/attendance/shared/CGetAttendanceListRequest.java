package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CGetAttendanceListRequest extends ClientOrderBasePacket {
    private final String userId;

    public CGetAttendanceListRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }



}
