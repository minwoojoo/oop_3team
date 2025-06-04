package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendPendingReq extends ClientOrderBasePacket implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private final String userId;

    public CFriendPendingReq(String userId) {
        this.userId = userId;
    }

    public String getUserId() { return userId; }
}
