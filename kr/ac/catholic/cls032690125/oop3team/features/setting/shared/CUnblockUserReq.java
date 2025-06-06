package kr.ac.catholic.cls032690125.oop3team.features.setting.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CUnblockUserReq extends ClientOrderBasePacket {
    private final String userId;
    private final String blockedUserName;

    public CUnblockUserReq(String userId, String blockedUserName) {
        super();
        this.userId = userId;
        this.blockedUserName = blockedUserName;
    }

    public String getUserId() {
        return userId;
    }

    public String getBlockedUserName() {
        return blockedUserName;
    }
} 