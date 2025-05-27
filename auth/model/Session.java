package auth.model;

import java.time.LocalDateTime;

public class Session {
    private String sessionId;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private boolean isActive;

    // 생성자, getter/setter 생략 (필요에 따라 추가)

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
