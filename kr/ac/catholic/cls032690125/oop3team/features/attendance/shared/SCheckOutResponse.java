package kr.ac.catholic.cls032690125.oop3team.features.attendance.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SCheckOutResponse extends ServerResponseBasePacket {
    private boolean success;
    private String message;

    public SCheckOutResponse() {
        // required for serialization
    }

    public SCheckOutResponse(long requestId, boolean success, String message) {
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
