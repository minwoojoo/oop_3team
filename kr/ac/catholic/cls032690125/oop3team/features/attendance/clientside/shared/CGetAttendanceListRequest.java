package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.io.Serializable;

public class CGetAttendanceListRequest extends ClientOrderBasePacket {
    private final String userId;

    public CGetAttendanceListRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }



}
