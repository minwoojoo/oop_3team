package kr.ac.catholic.cls032690125.oop3team.features.keyword.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SDeleteKeywordResponse extends ServerResponseBasePacket {
    private boolean success;
    private String message;

    public SDeleteKeywordResponse(long requestId,boolean success, String message) {
        super(requestId);
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
