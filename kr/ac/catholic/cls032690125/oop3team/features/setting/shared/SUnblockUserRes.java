package kr.ac.catholic.cls032690125.oop3team.features.setting.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SUnblockUserRes extends ServerResponseBasePacket {
    private final boolean success;

    public SUnblockUserRes(long requestId, boolean success) {
        super(requestId);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
} 