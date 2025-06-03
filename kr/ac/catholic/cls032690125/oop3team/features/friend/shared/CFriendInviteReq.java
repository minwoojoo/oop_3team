package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendInviteReq extends ClientOrderBasePacket implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String fromUserId;
    private String toUserId;

    public CFriendInviteReq(String fromUserId, String toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }
}
