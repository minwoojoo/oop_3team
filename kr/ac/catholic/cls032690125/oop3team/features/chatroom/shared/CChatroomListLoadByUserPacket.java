package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CChatroomListLoadByUserPacket extends ClientOrderBasePacket {
    private String userId;
    private boolean isPrivate;

    public CChatroomListLoadByUserPacket(String userId, boolean isPrivate) {
        super();
        this.userId = userId;
        this.isPrivate = isPrivate;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
