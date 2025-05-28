package kr.ac.catholic.cls032690125.oop3team.client;

import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseHandler;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.util.HashMap;
import java.util.Map;

public class ClientInteractor extends ClientResponseListener {
    private Map<Long, ClientInteractResponse> map = new HashMap<>();

    public ClientInteractor(Client client) { super(client); }

    public void register(long requestId, ClientInteractResponse response) {
        map.put(requestId, response);
    }

    @ClientResponseHandler(ServerResponseBasePacket.class)
    public void onServerResponse(ServerResponseBasePacket response) {
        if(map.containsKey(response.getRequestId())) {
            try{
                map.get(response.getRequestId()).run(response);
            } catch (Exception e) { }
            map.remove(response.getRequestId());
        }
    }
}
