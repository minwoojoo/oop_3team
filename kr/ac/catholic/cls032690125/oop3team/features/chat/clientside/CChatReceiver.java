package kr.ac.catholic.cls032690125.oop3team.features.chat.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseHandler;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseListener;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageBroadcastPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chatroom.clientside.CChatroomIndividualController;
import kr.ac.catholic.cls032690125.oop3team.models.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CChatReceiver extends ClientResponseListener {
    public CChatReceiver(Client client) {
        super(client);
    }
    /**
     * 전역 새 메시지 리스너 인터페이스
     */
    public interface NewMessageListener {
        void onNewMessage(int chatroomId, Message msg);
    }

    // 등록된 전역 새 메시지 리스너
    private final List<NewMessageListener> globalListeners = new CopyOnWriteArrayList<>();

    @ClientResponseHandler(SMessageBroadcastPacket.class)
    public void onMessageBroadcast(SMessageBroadcastPacket packet) {
        var cid = packet.getMessage().getChatroomId();
        try {
            for(var watch: watchlist) {
                if(cid == watch.getChatroom().getChatroomId())
                    watch.messageReceived(packet.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[CChatReceiver] 패킷 수신/핸들러 실행 중 예외 발생: " + e.getClass().getName() + " - " + e.getMessage());
        }

        // 2) 전역 리스너 호출
        for (NewMessageListener listener : globalListeners) {
            listener.onNewMessage(cid, packet.getMessage());
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

    /**
     * 전역 메시지 리스너 등록
     */
    public void addNewMessageListener(NewMessageListener listener) {
        globalListeners.add(listener);
    }

    /**
     * 전역 메시지 리스너 해제
     */
    public void removeNewMessageListener(NewMessageListener listener) {
        globalListeners.remove(listener);
    }
}
