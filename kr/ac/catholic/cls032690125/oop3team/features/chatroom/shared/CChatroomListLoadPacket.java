package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CChatroomListLoadPacket extends ClientOrderBasePacket {
    // true면 1:1 대화의 목록, false면 그룹 채팅방의 목록을 가져옴
    private boolean isPrivate;


    public CChatroomListLoadPacket(boolean isPrivate) {
        super();
        this.isPrivate = isPrivate;
    }

    public boolean isPrivate() { return isPrivate; }
}
