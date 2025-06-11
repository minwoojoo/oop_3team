package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Session implements Serializable {
    private static final long serialVersionUID = 45772002L;

    private String sessionId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private boolean isActive;

    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
}
