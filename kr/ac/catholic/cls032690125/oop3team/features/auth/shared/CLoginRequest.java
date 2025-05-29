package kr.ac.catholic.cls032690125.oop3team.features.auth.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CLoginRequest extends ClientOrderBasePacket {
    private String username;
    private String password;

    private Session prevSession = null;

    public CLoginRequest(String userid, String password) {
        this.username = userid;
        this.password = password;
    }

    public CLoginRequest(Session prevSession) {
        this.prevSession = prevSession;
    }

    public String getUserId() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Session getPrevSession() { return prevSession; }
}
