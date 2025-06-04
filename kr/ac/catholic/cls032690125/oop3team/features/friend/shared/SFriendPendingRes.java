package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SFriendPendingRes extends ServerResponseBasePacket implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private final UserProfile[] pendingRequests;

    public SFriendPendingRes(long requestId, UserProfile[] pendingRequests) {
        super(requestId);
        this.pendingRequests = pendingRequests;
    }

    public UserProfile[] getPendingRequests() { return pendingRequests; }
}
