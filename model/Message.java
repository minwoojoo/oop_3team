package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private int messengerID;
    private int chatRoomID;
    private int senderID;
    private int receiverID;
    private String content;
    private LocalDateTime sentAt = LocalDateTime.now();
    private int threadID;

    public Message() {};

    public Message(int messengerID, int chatRoomID, int senderID, int receiverID, String content, LocalDateTime sentAt, int threadID) {
        this.messengerID = messengerID;
        this.chatRoomID = chatRoomID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.content = content;
        this.sentAt = sentAt;
        this.threadID = threadID;
    }

    public Message(int messageId, int chatRoomId, int senderId, int receiverId, String content) {
        this.messengerID = messengerID;
        this.chatRoomID = chatRoomID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.content = content;
        this.sentAt = sentAt;
    }

    public int getMessengerID() {
        return messengerID;
    }

    public int getChatRoomID() {
        return chatRoomID;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setMessengerID(int messengerID) {
        this.messengerID = messengerID;
    }

    public void setChatRoomID(int chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messengerID=" + messengerID +
                ", chatRoomID=" + chatRoomID +
                ", senderID=" + senderID +
                ", receiverID=" + receiverID +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                ", threadID=" + threadID +
                '}';
    }
}
