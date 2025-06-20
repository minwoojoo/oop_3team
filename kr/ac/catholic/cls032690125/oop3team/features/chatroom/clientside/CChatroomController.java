package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.CChatroomCreatePacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.CChatroomListLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomCreatePacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomListPacket;

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
}