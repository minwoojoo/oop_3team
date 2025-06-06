package kr.ac.catholic.cls032690125.oop3team.features.setting.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CUserNameGetReq extends ClientOrderBasePacket {
    private final String userId;

    public CUserNameGetReq(String userId) {
        super();
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
} 