package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendDeleteReq extends ClientOrderBasePacket {
    private final String userId;
    private final String friendId;

    public CFriendDeleteReq(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public String getFriendId() {
        return friendId;
    }
}