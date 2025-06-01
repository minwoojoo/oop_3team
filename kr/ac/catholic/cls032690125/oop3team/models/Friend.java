package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Friend implements Serializable {
    private int key;
    private String userid;
    private String friendid;
    private byte blocked;
    private boolean pending;
    private LocalDateTime created;

    public int getKey() {
        return key;
    }

    public String getUserid() {
        return userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public byte getBlocked() {
        return blocked;
    }

    public boolean isPending() {
        return pending;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
