package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CChatroomGetByIdPacket extends ClientOrderBasePacket {
    private int roomId;

    public CChatroomGetByIdPacket(int roomId) {
        super();
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }
}
