package kr.ac.catholic.cls032690125.oop3team.features.friend.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

public class CFriendController extends StandardClientControl {
    public CFriendController(Client client) {
        super(client);
    }

    public void getFriendList(String userId,
                              ClientInteractResponse<ServerResponsePacketSimplefied<UserProfile[]>> response) {
        CFriendListReq req = new CFriendListReq();
        req.setUserId(userId);
        System.out.println("=== 친구목록 조회 요청 ===");
        System.out.println("요청한 사용자 ID: " + req.getUserId());

        ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>> wrapper =
                new ClientInteractResponseSwing<ServerResponsePacketSimplefied<UserProfile[]>>() {
                    @Override
                    protected void execute(ServerResponsePacketSimplefied<UserProfile[]> data) {
                        if (data.getData() != null) {
                            System.out.println("=== 친구목록 조회 결과 (CFriendController 래퍼) ===");
                            for (UserProfile friend : data.getData()) {
                                System.out.println("친구 ID: " + friend.getUserId() + ", 이름: " + friend.getName());
                            }
                            System.out.println("=====================");
                        } else {
                            System.out.println("친구목록 조회 실패 또는 친구가 없습니다.");
                        }

                        response.run(data);
                    }
                };

        client.request(req, wrapper);
    }

    public void searchFriendForInvite(String search, ClientInteractResponse<ServerResponsePacketSimplefied<UserProfile[]>> response) {
        client.request(new CFriendSearchReq(search), response);
    }

    public void inviteFriend(String fromUserId, String toUserId, ClientInteractResponseSwing<SFriendInviteRes> response) {
        try {
            client.request(new CFriendInviteReq(fromUserId, toUserId), response);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void replyInvite(String userid, boolean accepted) {
        try {
            client.send(new CFriendReplyReq(userid, accepted));
        } catch (Exception e) { e.printStackTrace();}
    }

    public void getPendingFriendRequests(String userId, ClientInteractResponseSwing<SFriendPendingRes> callback) {
        client.request(new CFriendPendingReq(userId), callback);
    }

    public void acceptFriendRequest(String myUserId, String requesterId) {
        try {
            client.send(new CFriendAcceptReq(myUserId, requesterId));
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void rejectFriendRequest(String userId, String requesterId) {
        try {
            client.request(new CFriendRejectReq(userId, requesterId), new ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>>() {
                @Override
                protected void execute(ServerResponsePacketSimplefied<Boolean> data) {
                    if (data.getData() != null && data.getData()) {
                        System.out.println("친구 요청 거절 성공");
                    } else {
                        System.out.println("친구 요청 거절 실패");
                    }
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void blockFriend(String userId, String friendId, ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>> response) {
        try {
            client.request(new CFriendBlockReq(userId, friendId), response);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void deleteFriend(String userId, String friendId, ClientInteractResponseSwing<ServerResponsePacketSimplefied<Boolean>> response) {
        try {
            client.request(new CFriendDeleteReq(userId, friendId), response);
        } catch (Exception e) { e.printStackTrace(); }
    }
}