package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.client.Client;

/**
 * 클라이언트 측 컨트롤러의 기본 클래스
 */
public abstract class StandardClientControl {
    protected final Client client;
    protected Client getClient() { return client; }

    public StandardClientControl(Client client) {
        this.client = client;
    }
}
