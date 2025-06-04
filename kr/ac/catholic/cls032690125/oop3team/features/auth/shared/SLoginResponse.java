package kr.ac.catholic.cls032690125.oop3team.features.auth.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

public class SLoginResponse extends ServerResponseBasePacket {
    private boolean success;
    private Session session;

    public SLoginResponse(long requestid, boolean success, Session session) {
        super(requestid);
        this.success = success;
        this.session = session;
    }

    public boolean isSuccess() { return success; }
    public Session getSession() { return session; }
}
