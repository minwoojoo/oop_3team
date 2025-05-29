package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendInviteReq extends ClientOrderBasePacket {
    private int userid;

    public CFriendInviteReq(int userid) {
        this.userid = userid;
    }
}
