package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendReplyReq extends ClientOrderBasePacket {
    private String userId;
    private boolean accepted;

    public CFriendReplyReq(String userid, boolean accepted) {
        super();
        userId = userid;
        this.accepted = accepted;
    }

    public String getUserId() { return userId; }
    public boolean isAccepted() { return accepted; }
}
