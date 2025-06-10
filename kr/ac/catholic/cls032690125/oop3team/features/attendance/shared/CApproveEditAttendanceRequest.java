package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CApproveEditAttendanceRequest extends ClientOrderBasePacket {
    private long editRequestId;
    private String managerId;   // ID của người phê duyệt (trường phòng)
    private boolean approved;   // true = đồng ý, false = từ chối


    public CApproveEditAttendanceRequest(long requestId, long editRequestId, String managerId, boolean approved) {
        super(requestId);
        this.editRequestId = editRequestId;
        this.managerId = managerId;
        this.approved = approved;

    }

    // getter / setter

    public long getEditRequestId() {
        return editRequestId;
    }

    public void setEditRequestId(long editRequestId) {
        this.editRequestId = editRequestId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}