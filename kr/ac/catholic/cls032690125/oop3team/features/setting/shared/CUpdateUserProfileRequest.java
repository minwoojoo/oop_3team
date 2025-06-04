package kr.ac.catholic.cls032690125.oop3team.features.setting.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CUpdateUserProfileRequest extends ClientOrderBasePacket {
    private final String userId;
    private final String displayName;
    private final String statusMessage;

    public CUpdateUserProfileRequest(String userId, String displayName, String statusMessage) {
        super();
        this.userId = userId;
        this.displayName = displayName;
        this.statusMessage = statusMessage;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
