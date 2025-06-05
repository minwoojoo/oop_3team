package kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.chat.clientside.CChatController;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.CMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.gui.ChatScreenBase;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomMemberListPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.shared.SChatroomThreadListPacket;
import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.models.MessageBuilder;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import javax.swing.*;

public final class CChatroomIndividualController extends StandardClientControl {
    private Chatroom chatroom;
    private ChatScreenBase screen;

    private CChatController chatcon;
    private CChatroomController chatroomController;

    public Chatroom getChatroom() { return chatroom; }
    public ChatScreenBase getScreen() { return screen; }

    public CChatroomIndividualController(Client client, Chatroom chatroom, ChatScreenBase screen) {
        super(client);
        this.chatroom = chatroom;
        this.screen = screen;

        this.chatcon = new CChatController(client);
        this.chatroomController = new CChatroomController(client);
    }

    public void messageReceived(Message message) {
        SwingUtilities.invokeLater(() -> {
            screen.onChatMessage(message);
        });
    }

    public void sendMessage(String message, ClientInteractResponse<ServerResponsePacketSimplefied<Boolean>> callback) {
        chatcon.sendMessage(
                new MessageBuilder()
                        .setChatroomId(chatroom.getChatroomId())
                        .setCurrentTime()
                        .setSenderId(client.getCurrentSession().getUserId())
                        .setContent(message)
                        .build(),
                callback
        );
    }

    public void initiateMessage(int amount, ClientInteractResponse<SMessageLoadPacket> callback) {
        loadMessages(0, -amount, callback);
    }

    public void loadMessages(long refPoint, int period, ClientInteractResponse<SMessageLoadPacket> callback) {
        client.request(new CMessageLoadPacket(chatroom.getChatroomId(), refPoint, period), callback);
    }

    public void getMemberList(ClientInteractResponse<SChatroomMemberListPacket> callback) {
        chatroomController.requestMemberList(chatroom.getChatroomId(), callback);
    }

    public void getThread(int parentId, boolean isOpened,ClientInteractResponse<SChatroomThreadListPacket> callback) {
        chatroomController.requestThreadRoomList(parentId, isOpened, callback);
    }
}
