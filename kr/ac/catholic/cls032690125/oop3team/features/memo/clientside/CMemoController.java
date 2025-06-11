package kr.ac.catholic.cls032690125.oop3team.features.memo.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.CMemoSavePacket;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.CMemoListRequestPacket;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.SMemoListResponsePacket;
import kr.ac.catholic.cls032690125.oop3team.features.memo.shared.CMemoUpdatePacket;

public class CMemoController {
    private final Client client;

    public CMemoController(Client client) {
        this.client = client;
    }

    public void saveMemo(String userId, long messageId, String memoText, ClientInteractResponse<ServerResponsePacketSimplefied<Boolean>> callback) {
        client.request(new CMemoSavePacket(userId, messageId, memoText), callback);
    }

    public void requestMemoList(String userId, ClientInteractResponse<SMemoListResponsePacket> callback) {
        client.request(new CMemoListRequestPacket(userId), callback);
    }

    public void updateMemo(String userId, String timestamp, String memoText, ClientInteractResponse<ServerResponsePacketSimplefied<Boolean>> callback) {
        client.request(new CMemoUpdatePacket(userId, timestamp, memoText), callback);
    }

    public void deleteMemo(String userId, String timestamp, ClientInteractResponse<ServerResponsePacketSimplefied<Boolean>> callback) {
        client.request(new CMemoUpdatePacket(userId, timestamp, true), callback);
    }
}
