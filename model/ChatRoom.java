package model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class ChatRoom implements Serializable {
    private int chatRoomID;
    private String chatRoomName;
    private int ownerID;
    private List<User> members;
    private List<Message> messages;
    private LocalTime createdAt = LocalTime.now();

    public ChatRoom(int chatRoomID, String chatRoomName, int ownerID, List<User> members, List<Message> messages, LocalTime createdAt) {
        this.chatRoomID = chatRoomID;
        this.chatRoomName = chatRoomName;
        this.ownerID = ownerID;
        this.members = members;
        this.messages = messages;
        this.createdAt = createdAt;
    }

    public int getChatRoomID() {
        return chatRoomID;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public LocalTime getCreatedAt() {
        return createdAt;
    }

    public void setChatRoomID(int chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
}
