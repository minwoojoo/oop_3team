package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.io.Serializable;

public class CGetAttendanceEditRequestList extends ClientOrderBasePacket implements Serializable {
    private String userId;

    public CGetAttendanceEditRequestList(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}