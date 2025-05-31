package kr.ac.catholic.cls032690125.oop3team.features.chat.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.CMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.CMessageSendPacket;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageLoadPacket;
import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

public final class CChatController extends StandardClientControl {
    public CChatController(Client client) {
        super(client);
    }

    /**
     * 메시지를 전송합니다.
     *
     * @param message 보낼 메시지
     * @param callback 콜백 (Nullable)
     */
    public void sendMessage(Message message, ClientInteractResponse<ServerResponsePacketSimplefied<Boolean>> callback) {
        client.request(new CMessageSendPacket(message), callback);
    }

    /**
     * 이전 메시지를 로딩합니다.
     *
     * @param chatroomId 채팅방(스레드) id
     * @param refPoint 기준점이 되는 메시지의 id (없으면 0)
     * @param period 불러올 메시지의 양 (양수면 기준점의 아래(기준점 이후의 채팅), 음수면 기준점의 위(기준점 이전의 채팅))
     * @param callback 콜백 함수
     */
    public void loadMessages(int chatroomId, long refPoint, int period, ClientInteractResponse<SMessageLoadPacket> callback) {
        client.request(new CMessageLoadPacket(chatroomId, refPoint, period), callback);
    }
}
