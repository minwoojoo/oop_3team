package kr.ac.catholic.cls032690125.oop3team.features.memo.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;
import java.io.Serializable;

public class CMemoUpdatePacket extends ClientOrderBasePacket implements Serializable {
    private String userId;
    private String timestamp; // 메모 식별자(생성 시각)
    private String memoText;  // 수정할 메모 내용
    private boolean isDelete; // true면 삭제, false면 수정

    public CMemoUpdatePacket(String userId, String timestamp, String memoText) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.memoText = memoText;
        this.isDelete = false;
    }

    public CMemoUpdatePacket(String userId, String timestamp, boolean isDelete) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.isDelete = isDelete;
    }

    public String getUserId() { return userId; }
    public String getTimestamp() { return timestamp; }
    public String getMemoText() { return memoText; }
    public boolean isDelete() { return isDelete; }
}
