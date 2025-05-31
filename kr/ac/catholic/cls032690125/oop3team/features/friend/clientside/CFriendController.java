package kr.ac.catholic.cls032690125.oop3team.features.friend.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendInviteReq;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendListReq;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendReplyReq;
import kr.ac.catholic.cls032690125.oop3team.features.friend.shared.CFriendSearchReq;
import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.models.responses.FriendSearchIndividual;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

public class CFriendController extends StandardClientControl {
    public CFriendController(Client client) {
        super(client);
    }

    public void getFriendList(ClientInteractResponse<ServerResponsePacketSimplefied<UserProfile[]>> response) {
        client.request(new CFriendListReq(), response);
    }

    public void searchFriendForInvite(String search, ClientInteractResponse<ServerResponsePacketSimplefied<FriendSearchIndividual[]>> response) {
        client.request(new CFriendSearchReq(search), response);
    }

    public void inviteFriend(String userid) {
        try {
            client.send(new CFriendInviteReq(userid));
        } catch (Exception e) { e.printStackTrace();}
    }

    public void replyInvite(String userid, boolean accepted) {
        try {
            client.send(new CFriendReplyReq(userid, accepted));
        } catch (Exception e) { e.printStackTrace();}
    }
}
