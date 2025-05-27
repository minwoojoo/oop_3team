package kr.ac.catholic.cls032690125.oop3team.auth.model;

import java.time.LocalDateTime;

public class User {
    private int userId;
    private String name;
    private String passwordHash;
    private boolean isOnline;
    private String workStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자, getter/setter 생략 (필요에 따라 추가)
}
