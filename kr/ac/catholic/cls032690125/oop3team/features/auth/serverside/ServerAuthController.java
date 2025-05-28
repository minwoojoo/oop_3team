package kr.ac.catholic.cls032690125.oop3team.features.auth.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.auth.shared.ClientLoginRequest;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

public class ServerAuthController extends ServerRequestListener {
    private final AuthDAO authDAO;

    public ServerAuthController(Server server) {
        super(server);
        authDAO = new AuthDAO(server);
    }

    @ServerRequestHandler(ClientLoginRequest.class)
    public void clientLogin(ServerClientHandler sch, ClientLoginRequest req) {
        ServerResponsePacketSimplefied<Boolean> res =
                new ServerResponsePacketSimplefied<>(
                        req.getRequestId(),
                        true //authDAO.checkLogin(req.getUsername(), req.getPassword())
                );
        sch.send(res);
    }
}
