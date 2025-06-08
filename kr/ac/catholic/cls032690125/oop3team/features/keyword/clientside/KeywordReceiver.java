package kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseHandler;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseListener;
import kr.ac.catholic.cls032690125.oop3team.features.chat.shared.SMessageBroadcastPacket;
import kr.ac.catholic.cls032690125.oop3team.models.Keyword;

import java.util.ArrayList;
import java.util.List;

public class KeywordReceiver extends ClientResponseListener {
    private List<Keyword> keywords = new ArrayList<>();

    public KeywordReceiver(Client client) {
        super(client);
    }

    @ClientResponseHandler(SMessageBroadcastPacket.class)
    public void onSMessageBroadcastPacket(SMessageBroadcastPacket packet) {
    }
}
