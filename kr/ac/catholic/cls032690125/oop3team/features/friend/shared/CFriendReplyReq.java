package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendReplyReq extends ClientOrderBasePacket {
    private int userId;
    private boolean accepted;

    public CFriendReplyReq(int userid, boolean accepted) {
        super();
        userId = userid;
        this.accepted = accepted;
    }

    public int getUserId() { return userId; }
    public boolean isAccepted() { return accepted; }
}
