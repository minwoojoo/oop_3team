package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CCheckInRequest extends ClientOrderBasePacket {
    private String userId;
    private int chatroomId;

    public CCheckInRequest() {};

    public CCheckInRequest(String userId, int chatroomId) {
        super();
        this.userId = userId;
        this.chatroomId = chatroomId;
    }

    public String getUserId() {
        return userId;
    }
    public int getChatroomId() {
        return chatroomId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }
}
