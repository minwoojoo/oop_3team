package kr.ac.catholic.cls032690125.oop3team.features.attendance.clientside.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SSubmitEditAttendanceResponse extends ServerResponseBasePacket {
    private boolean success;
    private String message;

    public SSubmitEditAttendanceResponse() {}

    public SSubmitEditAttendanceResponse(long requestId, boolean success, String message) {
        super(requestId);
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
