package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SChatroomThreadClosePacket extends ServerResponseBasePacket {
    private int threadId;

    public SChatroomThreadClosePacket(long requestId,int threadId) {
        super(requestId);
        this.threadId = threadId;
    }

    public int getThreadId() {
        return threadId;
    }

}
