package kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CAddKeywordRequest extends ClientOrderBasePacket {
    private String userId;
    private int chatroomId;
    private String keyword;

    public CAddKeywordRequest(String userId, int chatroomId, String keyword) {
        this.userId = userId;
        this.chatroomId = chatroomId;
        this.keyword = keyword;
    }

    public String getUserId() {
        return userId;
    }

    public int getChatroomId() {
        return chatroomId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
