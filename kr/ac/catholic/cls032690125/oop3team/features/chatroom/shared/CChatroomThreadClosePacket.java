package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CChatroomThreadClosePacket extends ClientOrderBasePacket {
    private final int threadId;
    private final String userId;

    public CChatroomThreadClosePacket(int threadId, String userId) {
        super();
        this.threadId = threadId;
        this.userId = userId;
    }

    public int getThreadId() {
        return threadId;
    }

    public String getUserId() {
        return userId;
    }
}
