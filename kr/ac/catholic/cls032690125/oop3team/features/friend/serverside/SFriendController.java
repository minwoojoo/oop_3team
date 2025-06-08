package kr.ac.catholic.cls032690125.oop3team.features.friend.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendRejectReq;

import java.util.List;

//TODO IMPL IT
public class SFriendController extends ServerRequestListener {
    private final FriendDAO friendDAO;

    public SFriendController(Server server) {
        super(server);
        this.friendDAO = new FriendDAO(server);
    }

    @ServerRequestHandler(CFriendListReq.class)
    public void getFriendList(ServerClientHandler sch, CFriendListReq req) {
        try {
            System.out.println("=== 친구목록 조회 요청 ===");
            System.out.println("요청한 사용자 ID: " + req.getUserId());
            
            // 친구목록 조회
            List<UserProfile> friends = friendDAO.getFriendList(req.getUserId());
            UserProfile[] resultArr = friends.toArray(new UserProfile[0]);
            
            System.out.println("조회된 친구 수: " + resultArr.length);
            for (UserProfile friend : resultArr) {
                System.out.println("친구 ID: " + friend.getUserId() + ", 이름: " + friend.getName());
            }
            System.out.println("=====================");
            
            // 응답 패킷 생성 및 전송
            ServerResponsePacketSimplefied<UserProfile[]> response =
                new ServerResponsePacketSimplefied<>(req.getRequestId(), resultArr);
            sch.send(response);
        } catch (Exception e) {
            System.out.println("친구목록 조회 중 오류 발생!");
            e.printStackTrace();
            // 실패 시 null 반환
            ServerResponsePacketSimplefied<UserProfile[]> response =
                new ServerResponsePacketSimplefied<>(req.getRequestId(), null);
            sch.send(response);
        }
    }

    @ServerRequestHandler(CFriendSearchReq.class)
    public void getFriendSearch(ServerClientHandler sch, CFriendSearchReq req) {
        try {
            // user 테이블에서 user_id가 정확히 일치하는 유저만 검색
            List<UserProfile> results = friendDAO.searchUser(req.getSearch());
            UserProfile[] resultArr = results.toArray(new UserProfile[0]);
            ServerResponsePacketSimplefied<UserProfile[]> response =
                new ServerResponsePacketSimplefied<>(req.getRequestId(), resultArr);
            sch.send(response);
        } catch (Exception e) {
            e.printStackTrace();
            ServerResponsePacketSimplefied<UserProfile[]> response =
                new ServerResponsePacketSimplefied<>(req.getRequestId(), null);
            sch.send(response);
        }
    }

    @ServerRequestHandler(CFriendInviteReq.class)
    public void inviteFriend(ServerClientHandler sch, CFriendInviteReq req) {
        try {
            // 이미 친구인지 확인
            if (friendDAO.isFriend(req.getFromUserId(), req.getToUserId())) {
                SFriendInviteRes response = new SFriendInviteRes(req.getRequestId(), false, "이미 추가된 친구입니다.");
                sch.send(response);
                return;
            }

            boolean success = friendDAO.inviteFriend(req.getFromUserId(), req.getToUserId());
            String message = success ? "친구 요청이 전송되었습니다." : "친구 요청에 실패했습니다.";
            SFriendInviteRes response = new SFriendInviteRes(req.getRequestId(), success, message);
            sch.send(response);
        } catch (Exception e) {
            e.printStackTrace();
            SFriendInviteRes response = new SFriendInviteRes(req.getRequestId(), false, "서버 오류가 발생했습니다.");
            sch.send(response);
        }
    }

    @ServerRequestHandler(CFriendReplyReq.class)
    public void replyFriend(ServerClientHandler sch, CFriendReplyReq req) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @ServerRequestHandler(CFriendPendingReq.class)
    public void getPendingFriendRequests(ServerClientHandler sch, CFriendPendingReq req) {
        try {
            List<UserProfile> results = friendDAO.getPendingFriendRequests(req.getUserId());
            UserProfile[] resultArr = results.toArray(new UserProfile[0]);
            SFriendPendingRes response = new SFriendPendingRes(req.getRequestId(), resultArr);
            sch.send(response);
        } catch (Exception e) {
            e.printStackTrace();
            SFriendPendingRes response = new SFriendPendingRes(req.getRequestId(), null);
            sch.send(response);
        }
    }

    @ServerRequestHandler(CFriendAcceptReq.class)
    public void acceptFriendRequest(ServerClientHandler sch, CFriendAcceptReq req) {
        try {
            friendDAO.acceptFriendRequest(req.getMyUserId(), req.getRequesterId());
            // 필요하다면 성공 응답 패킷 전송
        } catch (Exception e) {
            e.printStackTrace();
            // 실패 응답 패킷 전송 가능
        }
    }

    @ServerRequestHandler(CFriendRejectReq.class)
    public void handleFriendReject(ServerClientHandler sch, CFriendRejectReq req) {
        try {
            // 친구 요청 거절 시 해당 행 삭제
            friendDAO.deleteFriendRequest(req.getUserId(), req.getRequesterId());
            
            // 클라이언트에게 성공 응답 전송
            ServerResponsePacketSimplefied<Boolean> response = 
                new ServerResponsePacketSimplefied<>(req.getRequestId(), true);
            sch.send(response);
        } catch (Exception e) {
            e.printStackTrace();
            ServerResponsePacketSimplefied<Boolean> response = 
                new ServerResponsePacketSimplefied<>(req.getRequestId(), false);
            sch.send(response);
        }
    }

    @ServerRequestHandler(CFriendBlockReq.class)
    public void handleFriendBlock(ServerClientHandler sch, CFriendBlockReq req) {
        try {
            boolean success = friendDAO.blockFriend(req.getUserId(), req.getFriendId());
            ServerResponsePacketSimplefied<Boolean> response = 
                new ServerResponsePacketSimplefied<>(req.getRequestId(), success);
            sch.send(response);
        } catch (Exception e) {
            e.printStackTrace();
            ServerResponsePacketSimplefied<Boolean> response = 
                new ServerResponsePacketSimplefied<>(req.getRequestId(), false);
            sch.send(response);
        }
    }

    @ServerRequestHandler(CFriendDeleteReq.class)
    public void handleFriendDelete(ServerClientHandler sch, CFriendDeleteReq req) {
        try {
            boolean success = friendDAO.deleteFriend(req.getUserId(), req.getFriendId());
            ServerResponsePacketSimplefied<Boolean> response = 
                new ServerResponsePacketSimplefied<>(req.getRequestId(), success);
            sch.send(response);
        } catch (Exception e) {
            e.printStackTrace();
            ServerResponsePacketSimplefied<Boolean> response = 
                new ServerResponsePacketSimplefied<>(req.getRequestId(), false);
            sch.send(response);
        }
    }

    @ServerRequestHandler(CFriendCheckBlockedReq.class)
    public void handleCheckBlocked(ServerClientHandler sch, CFriendCheckBlockedReq req) {
        try {
            boolean isBlocked = friendDAO.isBlocked(req.getUserId(), req.getFriendId());
            ServerResponsePacketSimplefied<Boolean> response =
                new ServerResponsePacketSimplefied<>(req.getRequestId(), isBlocked);
            sch.send(response);
        } catch (Exception e) {
            e.printStackTrace();
            ServerResponsePacketSimplefied<Boolean> response =
                new ServerResponsePacketSimplefied<>(req.getRequestId(), false);
            sch.send(response);
        }
    }
}
