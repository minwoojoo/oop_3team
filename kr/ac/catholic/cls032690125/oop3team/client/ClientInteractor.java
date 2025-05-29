package kr.ac.catholic.cls032690125.oop3team.client;

import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseHandler;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientResponseListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.util.HashMap;
import java.util.Map;

/**
 * 클라이언트 내부 사용을 위한 클래스. 서버와의 통신 후 콜백을 실행합니다.
 */
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
                var callback = map.get(response.getRequestId());
                if (callback != null) callback.run(response);
            } catch (Exception e) { }
            map.remove(response.getRequestId());
        }
    }
}
