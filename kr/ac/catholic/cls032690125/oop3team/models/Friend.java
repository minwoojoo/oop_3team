package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Friend implements Serializable {
    private int key;
    private String userid;
    private String friendid;
    /// 0: not blocked, 1: blocked by 'user', 2: blocked by 'friend', 3: blocked by both
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
