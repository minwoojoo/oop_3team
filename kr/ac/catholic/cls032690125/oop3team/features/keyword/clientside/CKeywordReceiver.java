package kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponseSwing;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseHandler;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseListener;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageBroadcastPacket;
import kr.ac.catholic.cls032690125.oop3team.features.keyword.shared.CGetKeywordListRequest;
import kr.ac.catholic.cls032690125.oop3team.features.keyword.shared.SGetKeywordListResponse;
import kr.ac.catholic.cls032690125.oop3team.models.Keyword;

import java.util.ArrayList;
import java.util.List;

public class CKeywordReceiver extends ClientResponseListener {
    private List<Keyword> keywords = new ArrayList<Keyword>();

    private List<CKeywordReceiverHandler> handlers = new ArrayList<>();
    public void addHandler(CKeywordReceiverHandler handler) { handlers.add(handler); }

    public CKeywordReceiver(Client client) {
        super(client);
    }

    @ClientResponseHandler(SMessageBroadcastPacket.class)
    public void onSMessageBroadcast(SMessageBroadcastPacket packet) {
        var msg = packet.getMessage();
        for(Keyword keyword : keywords) {
            if(
                    msg.getContent().contains(keyword.getKeyword()) &&
                            msg.getChatroomId() == keyword.getChatRoomId()
            ) {
                for(CKeywordReceiverHandler handler : handlers) {
                    handler.handle(msg);
                }
                return;
            }
        }
    }

    public void updateKeywords() {
        client.request(new CGetKeywordListRequest(client.getCurrentSession().getUserId(), -1), (ClientInteractResponse<SGetKeywordListResponse>) data -> {
            keywords = data.getKeywords();
        });
    }
}
