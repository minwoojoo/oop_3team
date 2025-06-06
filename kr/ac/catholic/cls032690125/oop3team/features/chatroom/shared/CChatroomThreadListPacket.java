package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CChatroomThreadListPacket extends ClientOrderBasePacket {
    private int parentId;
    private boolean isOpened;

    public CChatroomThreadListPacket(int parentId, boolean isOpened) {
        this.isOpened = isOpened;
        this.parentId = parentId;
    }

    public int getParentId() {
        return parentId;
    }

    public boolean getIsOpened() {
        return isOpened;
    }
}
