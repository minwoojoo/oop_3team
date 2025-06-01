package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendInviteReq extends ClientOrderBasePacket {
    private String userid;

    public CFriendInviteReq(String userid) {
        this.userid = userid;
    }
}
