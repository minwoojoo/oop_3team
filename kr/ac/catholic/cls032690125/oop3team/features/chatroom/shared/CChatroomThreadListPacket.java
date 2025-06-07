package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CChatroomThreadListPacket extends ClientOrderBasePacket {
    private final int parentId;
    private final boolean isClosed;

    public CChatroomThreadListPacket(int parentId, boolean isClosed) {
        super();
        this.parentId = parentId;
        this.isClosed = isClosed;
    }

    public int getParentId() {
        return parentId;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
