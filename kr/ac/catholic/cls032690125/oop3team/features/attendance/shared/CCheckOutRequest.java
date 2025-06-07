package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CCheckOutRequest extends ClientOrderBasePacket {
    private String userId;

    public CCheckOutRequest() {
        // required for serialization
    }

    public CCheckOutRequest(String userId) {

        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
