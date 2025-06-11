package kr.ac.catholic.cls032690125.oop3team.features.auth.shared;

import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CIdDuplicateCheckRequest extends ClientOrderBasePacket {
    private String userId;

    public CIdDuplicateCheckRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
