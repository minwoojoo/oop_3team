package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

import java.io.Serializable;

public class CFriendRejectReq extends ClientOrderBasePacket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String userId;
    private final String requesterId;

    public CFriendRejectReq(String userId, String requesterId) {
        super();
        this.userId = userId;
        this.requesterId = requesterId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRequesterId() {
        return requesterId;
    }
} 