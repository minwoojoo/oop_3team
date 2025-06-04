package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendAcceptReq extends ClientOrderBasePacket implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private final String myUserId;
    private final String requesterId;

    public CFriendAcceptReq(String myUserId, String requesterId) {
        this.myUserId = myUserId;
        this.requesterId = requesterId;
    }

    public String getMyUserId() { return myUserId; }
    public String getRequesterId() { return requesterId; }
}
