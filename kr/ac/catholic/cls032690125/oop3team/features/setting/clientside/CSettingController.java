package kr.ac.catholic.cls032690125.oop3team.features.setting.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.features.setting.shared.*;
import java.util.function.Consumer;

public class CSettingController {
    private final Client client;

    public CSettingController(Client client) {
        this.client = client;
    }

    public void updateWorkStatus(String userId, String workStatus, ClientInteractResponse<SWorkStatusUpdateRes> callback) {
        CWorkStatusUpdateReq req = new CWorkStatusUpdateReq(userId, workStatus);
        client.request(req, callback);
    }

    public void getWorkStatus(String userId, ClientInteractResponse<SWorkStatusGetRes> callback) {
        CWorkStatusGetReq req = new CWorkStatusGetReq(userId);
        client.request(req, callback);
    }

    public void getBlockedList(String userId, ClientInteractResponse<SBlockListRes> callback) {
        CBlockListReq req = new CBlockListReq(userId);
        client.request(req, callback);
    }

    public void unblockUser(String userId, String blockedUserName, ClientInteractResponse<SUnblockUserRes> callback) {
        CUnblockUserReq req = new CUnblockUserReq(userId, blockedUserName);
        client.request(req, callback);
    }

    private void sendRequest(ClientOrderBasePacket req, ClientInteractResponse callback) {
        client.request(req, callback);
    }

    public void getUserName(String userId, Consumer<ServerResponsePacketSimplefied<String>> callback) {
        CUserNameGetReq req = new CUserNameGetReq(userId);
        client.request(req, new ClientInteractResponse() {
            @Override
            public void run(ServerResponseBasePacket response) {
                if (response instanceof SUserNameGetRes) {
                    SUserNameGetRes res = (SUserNameGetRes) response;
                    callback.accept(new ServerResponsePacketSimplefied<String>(req.getRequestId(), res.getName()));
                } else {
                    callback.accept(new ServerResponsePacketSimplefied<String>(req.getRequestId(), null));
                }
            }
        });
    }
}
