package kr.ac.catholic.cls032690125.oop3team.features.auth.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.auth.shared.CLoginRequest;
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

    @ServerRequestHandler(CLoginRequest.class)
    public void clientLogin(ServerClientHandler sch, CLoginRequest req) {
        ServerResponsePacketSimplefied<Boolean> res =
                new ServerResponsePacketSimplefied<>(
                        req.getRequestId(),
                        authDAO.checkLogin(req.getUsername(), req.getPassword())
                );
        sch.send(res);
    }
}
