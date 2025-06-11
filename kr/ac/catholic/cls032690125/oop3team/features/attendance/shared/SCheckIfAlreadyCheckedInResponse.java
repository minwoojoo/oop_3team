package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SCheckIfAlreadyCheckedInResponse extends ServerResponseBasePacket {
    private final boolean success;
    private final boolean alreadyCheckedIn;

    public SCheckIfAlreadyCheckedInResponse(long requestId, boolean success, boolean alreadyCheckedIn) {
        super(requestId);
        this.success = success;
        this.alreadyCheckedIn = alreadyCheckedIn;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isAlreadyCheckedIn() {
        return alreadyCheckedIn;
    }
}
