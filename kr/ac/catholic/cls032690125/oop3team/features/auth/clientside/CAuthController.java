package kr.ac.catholic.cls032690125.oop3team.features.auth.clientside;

import kr.ac.catholic.cls032690125.oop3team.client.Client;
import kr.ac.catholic.cls032690125.oop3team.client.structs.ClientInteractResponse;
import kr.ac.catholic.cls032690125.oop3team.client.structs.StandardClientControl;
import kr.ac.catholic.cls032690125.oop3team.features.auth.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.models.responses.SignupResult;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

public class CAuthController extends StandardClientControl {
    public CAuthController(Client client) {
        super(client);
    }

    public void sendLogin(String id, String password, ClientInteractResponse<SLoginResponse> callback) {
        client.request(new CLoginRequest(id, password), (ClientInteractResponse<SLoginResponse>) response -> {
            if(response.isSuccess()) client.updateSession(response.getSession());
            if(callback != null) callback.run(response);
        });
    }

    public void sendSignUp(String id, String password, String username, ClientInteractResponse<ServerResponsePacketSimplefied<SignupResult>> callback) {
        client.request(new CSignupRequest(id, password, username), callback);
    }

    public void refreshSession(ClientInteractResponse<ServerResponsePacketSimplefied<Session>> callback) {
        client.request(new CRefreshSessionRequest(), (ClientInteractResponse<ServerResponsePacketSimplefied<Session>>) response -> {
            client.updateSession(response.getData());
            if(callback != null) callback.run(response);
        });
    }

    public void sendLogout(ClientInteractResponse<ServerResponsePacketSimplefied<Boolean>> callback) {
        client.request(new CLogoutRequest(), callback);
    }
}
