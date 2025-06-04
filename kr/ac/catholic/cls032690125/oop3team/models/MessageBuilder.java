package kr.ac.catholic.cls032690125.oop3team.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageBuilder {
    private long messageId = -1;
    private int chatroomId;

    private String senderId;
    private LocalDateTime sent;
    private boolean isSystem = false;
    private String content;

    public MessageBuilder() {}

    public MessageBuilder setCurrentTime() {
        this.sent = LocalDateTime.now();
        return this;
    }

    public MessageBuilder setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
        return this;
    }

    public MessageBuilder setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public MessageBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MessageBuilder setSystem(boolean system) {
        this.isSystem = system;
        return this;
    }

    public Message build() {
        return new Message(messageId, chatroomId, senderId, content, isSystem, sent);
    }
}
