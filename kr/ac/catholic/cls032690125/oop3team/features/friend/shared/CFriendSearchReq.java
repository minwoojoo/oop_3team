package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendSearchReq extends ClientOrderBasePacket {
    private String search;

    public CFriendSearchReq(String search) {
        super();
        this.search = search;
    }

    public String getSearch() { return search; }
}
