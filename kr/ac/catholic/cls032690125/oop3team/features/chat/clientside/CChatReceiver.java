package kr.ac.catholic.cls032690125.oop3team.features.chat.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseHandler;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseListener;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageBroadcastPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;

import java.util.ArrayList;
import java.util.List;

public class CChatReceiver extends ClientResponseListener {
    public CChatReceiver(Client client) {
        super(client);
    }

    @ClientResponseHandler(SMessageBroadcastPacket.class)
    public void onMessageBroadcast(SMessageBroadcastPacket packet) {
        var cid = packet.getMessage().getChatroomId();
        for(var watch: watchlist) {
            if(cid == watch.getChatroom().getChatroomId())
                watch.messageReceived(packet.getMessage());
        }
    }

    private List<CChatroomIndividualController> watchlist = new ArrayList<>();

    /**
     * 채팅방 GUI가 생성되면 이 함수를 호출하세요
     *
     * @param controller GUI의 채팅방 컨트롤러
     */
    public void registerChatroom(CChatroomIndividualController controller) {
        watchlist.add(controller);
    }
}
