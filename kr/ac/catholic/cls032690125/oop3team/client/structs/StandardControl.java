package kr.ac.catholic.cls032690125.oop3team.client.structs;

import kr.ac.catholic.cls032690125.oop3team.client.Client;

public abstract class StandardControl {
    protected final Client client;
    protected Client getClient() { return client; }

    public StandardControl(Client client) {
        this.client = client;
    }
}
