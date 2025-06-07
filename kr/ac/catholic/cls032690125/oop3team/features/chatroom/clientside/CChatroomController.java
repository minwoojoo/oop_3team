package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.*;

import java.util.List;

public class CChatroomController extends StandardClientControl {
    public CChatroomController(Client client) {
        super(client);
    }

    public void requestChatroomList(boolean isPrivate, ClientInteractResponse<SChatroomListPacket> callback) {
        client.request(new CChatroomListLoadPacket(isPrivate), callback);
    }

    public void sendCreateChatroom(CChatroomCreatePacket cChatroomCreatePacket, ClientInteractResponse<SChatroomCreatePacket> callback) {
        client.request(cChatroomCreatePacket, callback);
    }

    /**
     * 채팅방 ID를 주면, 서버에 참가자 목록 요청을 보내고 콜백을 통해 List<String>을 받는다.
     * @param chatroomId 조회할 채팅방 ID
     * @param callback   요청 완료 후 호출할 콜백 (멤버 리스트를 넘겨줌)
     */
    public void requestMemberList(int chatroomId, ClientInteractResponse<SChatroomMemberListPacket> callback) {
        CChatroomMemberListPacket cChatroomMemberListPacket = new CChatroomMemberListPacket(chatroomId);
        client.request(cChatroomMemberListPacket, callback);
    }

    public void requestThreadRoomList(int parentId, boolean isOpened, ClientInteractResponse<SChatroomThreadListPacket> callback) {
        CChatroomThreadListPacket cChatroomThreadListPacket = new CChatroomThreadListPacket(parentId, isOpened);
        client.request(cChatroomThreadListPacket, callback);
    }

    public void requestThreadRoomClose(int threadId, ClientInteractResponse<SChatroomThreadClosePacket> callback) {
        CChatroomThreadClosePacket cChatroomThreadClosePacket = new CChatroomThreadClosePacket(threadId);
        client.request(cChatroomThreadClosePacket, callback);
    }

    public void requestPrivateChatroom(String userAId, String userBId, ClientInteractResponse<SPrivateChatRoomResponsePacket> callback) {
        CPrivateChatRoomRequestPacket cPrivateChatRoomRequestPacket = new CPrivateChatRoomRequestPacket(userAId, userBId);
        client.request(cPrivateChatRoomRequestPacket, callback);
    }

    public void requestChatroomListByUserId(String userID, boolean isPrivate, ClientInteractResponse<SChatroomListPacket> callback) {
        client.request(new CChatroomListLoadByUserPacket(userID, isPrivate), callback);
    }
}