package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendCheckBlockedReq extends ClientOrderBasePacket {
    private String userId;
    private String friendId;

    public CFriendCheckBlockedReq(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getUserId() { return userId; }
    public String getFriendId() { return friendId; }
}
