package kr.ac.catholic.cls032690125.oop3team.features.auth.serverside;

import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;

public class SessionController extends ServerRequestListener {
    public SessionController(Server client) {
        super(client);
    }
}
