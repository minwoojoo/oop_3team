package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendListReq extends ClientOrderBasePacket implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;

    public CFriendListReq() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
