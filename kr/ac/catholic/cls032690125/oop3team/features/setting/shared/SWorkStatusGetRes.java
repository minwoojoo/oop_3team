package kr.ac.catholic.cls032690125.oop3team.features.setting.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SWorkStatusGetRes extends ServerResponseBasePacket {
    private final boolean success;
    private final String workStatus;

    public SWorkStatusGetRes(long requestId, boolean success, String workStatus) {
        super(requestId);
        this.success = success;
        this.workStatus = workStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getWorkStatus() {
        return workStatus;
    }
} 