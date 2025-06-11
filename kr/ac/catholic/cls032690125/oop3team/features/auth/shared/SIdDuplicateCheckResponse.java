package kr.ac.catholic.cls032690125.oop3team.features.auth.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SIdDuplicateCheckResponse extends ServerResponseBasePacket {
    private boolean isDuplicate;

    public SIdDuplicateCheckResponse(long requestId, boolean isDuplicate) {
        super(requestId);
        this.isDuplicate = isDuplicate;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }
}
