package kr.ac.catholic.cls032690125.oop3team.features.memo.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CMemoSavePacket extends ClientOrderBasePacket {
    private String userId;
    private long messageId;
    private String memoText;

    public CMemoSavePacket(String userId, long messageId, String memoText) {
        this.userId = userId;
        this.messageId = messageId;
        this.memoText = memoText;
    }

    public String getUserId() { return userId; }
    public long getMessageId() { return messageId; }
    public String getMemoText() { return memoText; }
}
