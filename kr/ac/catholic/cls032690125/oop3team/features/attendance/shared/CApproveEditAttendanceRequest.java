package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CApproveEditAttendanceRequest extends ClientOrderBasePacket {
    private long editRequestId;
    private boolean approved;

    public CApproveEditAttendanceRequest(long requestId, long editRequestId, boolean approved) {
        super(requestId);
        this.editRequestId = editRequestId;
        this.approved = approved;
    }

    public long getEditRequestId() {
        return editRequestId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setEditRequestId(long editRequestId) {
        this.editRequestId = editRequestId;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
