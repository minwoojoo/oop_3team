package kr.ac.catholic.cls032690125.oop3team.models.responses;

public class UserProfile implements java.io.Serializable {
    private static final long serialVersionUID = 45772011L;

    private final String userId;
    private final String name;
    private final boolean isOnline;
    private final String workStatus;
    //TODO: ADD FRIEND

    public UserProfile(String userId, String name, boolean isOnline, String workStatus) {
        this.userId = userId;
        this.name = name;
        this.isOnline = isOnline;
        this.workStatus = workStatus;
    }

    public UserProfile(String userId, String name) {
        this(userId, name, false, null); // 기본값은 오프라인
    }

    public UserProfile(String userId, String name, boolean isOnline) {
        this(userId, name, isOnline, null); // workStatus는 null로 기본값
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getWorkStatus() {
        return workStatus;
    }
}