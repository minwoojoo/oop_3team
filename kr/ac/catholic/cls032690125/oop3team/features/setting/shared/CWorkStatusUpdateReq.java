package kr.ac.catholic.cls032690125.oop3team.features.setting.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CWorkStatusUpdateReq extends ClientOrderBasePacket {
    private final String userId;
    private final String workStatus;

    public CWorkStatusUpdateReq(String userId, String workStatus) {
        super();
        this.userId = userId;
        this.workStatus = workStatus;
    }

    public String getUserId() {
        return userId;
    }

    public String getWorkStatus() {
        return workStatus;
    }
} 