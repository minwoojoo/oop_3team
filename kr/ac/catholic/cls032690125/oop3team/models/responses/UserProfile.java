package kr.ac.catholic.cls032690125.oop3team.models.responses;

public class UserProfile implements java.io.Serializable {
    private final String userId;
    private final String name;

    public UserProfile(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}