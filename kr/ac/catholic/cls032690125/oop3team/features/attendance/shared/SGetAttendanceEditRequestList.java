
package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.models.AttendanceEditRequest;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.util.List;

public class SGetAttendanceEditRequestList extends ServerResponseBasePacket {
    private boolean success;
    private String message;
    private List<AttendanceEditRequest> requests;

    public SGetAttendanceEditRequestList() {
    }

    public SGetAttendanceEditRequestList(long requestId,boolean success, String message, List<AttendanceEditRequest> requests) {
        super(requestId);
        this.success = success;
        this.message = message;
        this.requests = requests;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<AttendanceEditRequest> getRequests() {
        return requests;
    }
}
