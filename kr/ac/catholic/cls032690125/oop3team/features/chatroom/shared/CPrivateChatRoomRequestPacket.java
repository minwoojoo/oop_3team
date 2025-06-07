package kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CPrivateChatRoomRequestPacket extends ClientOrderBasePacket {
    private String userA;
    private String userB;

    public CPrivateChatRoomRequestPacket(String userA, String userB) {
        super();
        this.userA = userA;
        this.userB = userB;
    }

    public String getUserA() {
        return userA;
    }

    public String getUserB() {
        return userB;
    }
}
