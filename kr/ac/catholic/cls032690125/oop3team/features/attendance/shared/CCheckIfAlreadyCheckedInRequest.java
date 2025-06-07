package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CCheckIfAlreadyCheckedInRequest extends ClientOrderBasePacket {
    private final String userId;
    private final String date; // Format: yyyy-MM-dd

    public CCheckIfAlreadyCheckedInRequest(String userId, String date) {
        this.userId = userId;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }
}
