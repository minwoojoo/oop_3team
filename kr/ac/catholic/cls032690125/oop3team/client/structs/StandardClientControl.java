package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.client.Client;

public abstract class StandardClientControl {
    protected final Client client;
    protected Client getClient() { return client; }

    public StandardClientControl(Client client) {
        this.client = client;
    }
}
