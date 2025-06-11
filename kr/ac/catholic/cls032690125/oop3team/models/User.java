package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private static final long serialVersionUID = 45772001L;

    private String id;
    private String name;
    private String passwordHash;
    private boolean isOnline;
    private String workStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {}
}
