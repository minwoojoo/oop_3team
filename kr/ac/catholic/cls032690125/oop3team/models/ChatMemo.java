package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;

public class ChatMemo implements Serializable {
    private static final long serialVersionUID = 45772009L;

    private String timestamp;
    private String chatContent;
    private String memo;

    public ChatMemo(String timestamp, String chatContent, String memo) {
        this.timestamp = timestamp;
        this.chatContent = chatContent;
        this.memo = memo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
