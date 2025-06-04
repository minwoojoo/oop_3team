package kr.ac.catholic.cls032690125.oop3team.features.friend.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CFriendSearchReq extends ClientOrderBasePacket implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String search;

    public CFriendSearchReq(String search) {
        super();
        this.search = search;
    }

    public String getSearch() { return search; }
}
