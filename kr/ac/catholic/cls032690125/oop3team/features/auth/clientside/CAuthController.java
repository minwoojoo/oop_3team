package kr.ac.catholic.cls032690125.oop3team.features.auth.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.auth.clientside.gui.LoginScreen;
import kr.ac.catholic.cls032690125.oop3team.models.Session;

public class CAuthController extends StandardClientControl {
    public CAuthController(Client client) {
        super(client);
    }

    private Session currentSession;

    public void sendLogin(String id, String password) {
    }
}
