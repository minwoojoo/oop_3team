package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Chatroom implements Serializable {
    private static final long serialVersionUID = 45772008L;

    // 스레드일 경우 이곳에 스레드 id
    private int chatroomId;

    // 스레드일 경우에만 넣음
    private int parentroomId;
    private boolean closed;

    // 1:1대화
    private boolean isPrivate;

    private String title;
    private LocalDateTime created; 

    public int getChatroomId() {
        return chatroomId;
    }

    public int getParentroomId() {
        return parentroomId;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreated() { 
        return created;
    }

    public void setChatroomId(int chatroomId) {
        this.chatroomId = chatroomId;
    }

    public void setParentroomId(int parentroomId) {
        this.parentroomId = parentroomId;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreated(LocalDateTime created) { 
        this.created = created;
    }
}
