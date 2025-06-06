package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Attendance;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.io.Serializable;
import java.util.List;

public class SGetAttendanceListResponse extends ServerResponseBasePacket {
    private final boolean success;
    private final List<Attendance> records;
    private final String message;

    public SGetAttendanceListResponse(long requestId,boolean success, List<Attendance> records, String message) {
        super(requestId);
        this.success = success;
        this.records = records;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Attendance> getRecords() {
        return records;
    }

    public String getMessage() {
        return message;
    }


}
