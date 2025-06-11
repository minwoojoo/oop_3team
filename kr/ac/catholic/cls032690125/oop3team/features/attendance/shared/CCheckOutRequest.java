package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CCheckOutRequest extends ClientOrderBasePacket {
    private String userId;
    private int chatroomId;

    public CCheckOutRequest() {
        // required for serialization
    }

    public CCheckOutRequest(String userId,int chatroomId) {
        super();
        this.userId = userId;
        this.chatroomId = chatroomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getChatroomId() {
        return chatroomId;
    }
    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }
}
