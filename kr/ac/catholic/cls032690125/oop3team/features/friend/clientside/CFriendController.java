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

    public void getFriendList(ClientInteractResponse<ServerResponsePacketSimplefied<UserProfile[]>> response) {
        client.request(new CFriendListReq(), response);
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
}
