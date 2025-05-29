package kr.ac.catholic.cls032690125.oop3team.features.auth.serverside;

import kr.ac.catholic.cls032690125.oop3team.features.auth.shared.*;
import kr.ac.catholic.cls032690125.oop3team.models.Session;
import kr.ac.catholic.cls032690125.oop3team.models.responses.SignupResult;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.ServerClientHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestHandler;
import kr.ac.catholic.cls032690125.oop3team.server.structs.ServerRequestListener;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponsePacketSimplefied;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ServerAuthController extends ServerRequestListener {
    private final AuthDAO authDAO;
    private Map<String, Session> sessionMap = new HashMap<>();
    private final int SESSION_TIMEOUT_MINUTES = 60; // 1시간

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    public ServerAuthController(Server server) {
        super(server);
        authDAO = new AuthDAO(server);
    }

    @ServerRequestHandler(CLoginRequest.class)
    public void clientLogin(ServerClientHandler sch, CLoginRequest req) {
        if(!authDAO.checkLogin(req.getUserId(), hashPassword(req.getPassword())))
            sch.send(new SLoginResponse(req.getRequestId(), false, null));
        else {
            var session = createSession(req.getUserId());
            sch.updateSession(session);
            sch.send(new SLoginResponse(req.getRequestId(), true, session));
        }
    }

    @ServerRequestHandler(CSignupRequest.class)
    public void clientSignup(ServerClientHandler sch, CSignupRequest req) {
        if(authDAO.isIdDuplicate(req.getUsername()))
            sch.send(new ServerResponsePacketSimplefied<>(req.getRequestId(), SignupResult.DUPLICATED));
        else
            sch.send(new ServerResponsePacketSimplefied<>(
                    req.getRequestId(),
                    authDAO.insertUser(req.getUserid(), req.getUsername(), hashPassword(req.getPassword())) ?
                            SignupResult.SUCCESS : SignupResult.FAILED
            ));
    }

    @ServerRequestHandler(CRefreshSessionRequest.class)
    public void clientRefreshSession(ServerClientHandler sch, CRefreshSessionRequest req) {
        if(sch.getSession() == null)
            sch.send(new ServerResponsePacketSimplefied<Session>(req.getRequestId(), null));
        else {
            var session = refreshSession(sch.getSession().getUserId());
            sch.updateSession(session);
            sch.send(new ServerResponsePacketSimplefied<>(req.getRequestId(), session));
        }
    }

    @ServerRequestHandler(CLogoutRequest.class)
    public void clientLogout(ServerClientHandler sch, CLogoutRequest req) {
        if(sch.getSession() == null)
            sch.send(new ServerResponsePacketSimplefied<>(req.getRequestId(), false));
        else {
            removeSession(sch.getSession().getUserId());
            sch.updateSession(null);
            sch.send(new ServerResponsePacketSimplefied<>(req.getRequestId(), true));
        }
    }


    // 로그인 성공 시 세션 생성
    public Session createSession(String userId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMinutes(SESSION_TIMEOUT_MINUTES);
        Session session = new Session();
        session.setSessionId(userId + "_" + now.toString());
        session.setUserId(userId);
        session.setCreatedAt(now);
        session.setExpiredAt(expiredAt);
        session.setActive(true);
        sessionMap.put(userId, session);

        // DB에 세션 저장
        authDAO.insertSession(session);

        return session;
    }

    // 세션 만료 체크 및 갱신
    public boolean isSessionActive(String userId) {
        Session session = sessionMap.get(userId);
        if (session == null) return false;
        if (session.getExpiredAt().isBefore(LocalDateTime.now())) {
            session.setActive(false);
            return false;
        }
        return session.isActive();
    }

    // 세션 갱신 (활동 시 만료 시간 연장)
    public Session refreshSession(String userId) {
        Session session = sessionMap.get(userId);
        if (session != null && session.isActive()) {
            session.setExpiredAt(LocalDateTime.now().plusMinutes(SESSION_TIMEOUT_MINUTES));
        }
        return session;
    }

    // 로그아웃 또는 만료 시 세션 삭제
    public void removeSession(String userId) {
        sessionMap.remove(userId);
        // DB에서도 세션 삭제
        authDAO.removeSessionFromDB(userId);
    }
}
