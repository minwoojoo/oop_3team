package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private long messageId; // Serverside에서 지정함
    private int chatroomId;

    private String senderId;
    private LocalDateTime sent;
    private boolean isSystem; // TODO: 이거 쓰는거 맞나요?
    private String content;

    public Message(long messageId, int chatroomId, String senderId, String content, boolean isSystem, LocalDateTime sent) {
        this.messageId = messageId;
        this.chatroomId = chatroomId;
        this.senderId = senderId;
        this.content = content;

        this.isSystem = isSystem;
        this.sent = sent;
    }


    public long getMessageId() {
        return messageId;
    }

    public int getChatroomId() {
        return chatroomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public LocalDateTime getSent() {
        return sent;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public String getContent() {
        return content;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSent(LocalDateTime sent) {
        this.sent = sent;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
