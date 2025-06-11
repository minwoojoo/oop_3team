package kr.ac.catholic.cls032690125.oop3team.features.memo.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;
import java.io.Serializable;

/**
 * 클라이언트 → 서버 : 메모 목록 요청 패킷
 */
public class CMemoListRequestPacket extends ClientOrderBasePacket implements Serializable {
    private String userId;

    public CMemoListRequestPacket(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}