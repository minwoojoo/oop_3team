package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CSubmitEditAttendanceRequest extends ClientOrderBasePacket {
    private String userId;
    private String date;
    private String checkIn;
    private String checkOut;
    private String reason;

    public CSubmitEditAttendanceRequest(long requestId, String userId, String date, String checkIn, String checkOut, String reason) {
        super(requestId);
        this.userId = userId;
        this.date = date;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getReason() {
        return reason;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
