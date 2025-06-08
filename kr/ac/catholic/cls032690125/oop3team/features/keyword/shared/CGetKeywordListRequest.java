package kr.ac.catholic.cls032690125.oop3team.features.keyword.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CGetKeywordListRequest extends ClientOrderBasePacket {
    private String userId;
    private int chatroomId;

    public CGetKeywordListRequest(String userId, int chatroomId) {
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
