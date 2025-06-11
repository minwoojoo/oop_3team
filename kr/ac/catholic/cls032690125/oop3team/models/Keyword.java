package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Keyword implements Serializable {
    private static final long serialVersionUID = 45772005L;

    private int id;
    private String userId;
    private int chatRoomId;
    private String keyword;
    private java.sql.Timestamp createAt;

    public Keyword(int id, String userId, int chatRoomId, String keyword, Timestamp createAt) {
        this.id = id;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.keyword = keyword;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public String getKeyword() {
        return keyword;
    }
    public java.sql.Timestamp getCreateAt() {
        return createAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public void setCreateAt(java.sql.Timestamp createAt) {
        this.createAt = createAt;
    }
}

