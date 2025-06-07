package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CChatroomThreadClosePacket extends ClientOrderBasePacket {
    private int threadId;

    public CChatroomThreadClosePacket(int threadId) {
        super();
        this.threadId = threadId;
    }

    public int getThreadId() {
        return threadId;
    }
}
