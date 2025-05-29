package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class User implements Serializable {
    private int userId;
    private String userName;
    private String passwordHash;
    private boolean isOnline;
    private String workStatus;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();
    private List<User> friends;

    public User() {};

    public User(int userId, String userName, String password, boolean isOnline,
                String workStatus) {
        this.userId = userId;
        this.userName = userName;
        this.passwordHash = password;
        this.isOnline = isOnline;
        this.workStatus = workStatus;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();


    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<User> getFriends() {
        return friends;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", isOnline=" + isOnline +
                ", workStatus='" + workStatus + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", friends=" + friends +
                '}';
    }
}
