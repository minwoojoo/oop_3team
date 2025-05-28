package kr.ac.catholic.cls032690125.oop3team.features.friend.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendInviteReq;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendListReq;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendReplyReq;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendSearchReq;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;

//TODO IMPL IT
public class SFriendController extends ServerRequestListener {
    public SFriendController(Server server) { super(server); }

    @ServerRequestHandler(CFriendListReq.class)
    public void getFriendList(ServerClientHandler sch, CFriendListReq req) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @ServerRequestHandler(CFriendSearchReq.class)
    public void getFriendSearch(ServerClientHandler sch, CFriendSearchReq req) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @ServerRequestHandler(CFriendInviteReq.class)
    public void inviteFriend(ServerClientHandler sch, CFriendInviteReq req) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @ServerRequestHandler(CFriendReplyReq.class)
    public void replyFriend(ServerClientHandler sch, CFriendReplyReq req) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
