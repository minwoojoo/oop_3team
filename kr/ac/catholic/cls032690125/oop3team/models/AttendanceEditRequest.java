package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class AttendanceEditRequest implements Serializable {
    private long id;
    private String userId;
    private Timestamp attendanceDate;
    private String  requestedCheckIn;
    private String requestedCheckOut;
    private String reason;
    private Timestamp requestedAt;
    private String status;

    public AttendanceEditRequest() {}


    public AttendanceEditRequest(long id, String userId, Timestamp attendanceDate, String requestedCheckIn, String requestedCheckOut, String reason, Timestamp requestedAt, String status) {
        this.id = id;
        this.userId = userId;
        this.attendanceDate = attendanceDate;
        this.requestedCheckIn = requestedCheckIn;
        this.requestedCheckOut = requestedCheckOut;
        this.reason = reason;
        this.requestedAt = requestedAt;
        this.status = status;
    }

// Getter / Setter

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getAttendanceDate() {
        return attendanceDate;
    }

    public String getRequestedCheckIn() {
        return requestedCheckIn;
    }

    public String getRequestedCheckOut() {
        return requestedCheckOut;
    }

    public String getReason() {
        return reason;
    }

    public Timestamp getRequestedAt() {
        return requestedAt;
    }

    public String getStatus() {
        return status;
    }
}