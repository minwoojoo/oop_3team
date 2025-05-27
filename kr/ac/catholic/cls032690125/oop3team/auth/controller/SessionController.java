package kr.ac.catholic.cls032690125.oop3team.auth.controller;

import kr.ac.catholic.cls032690125.oop3team.auth.model.Session;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import kr.ac.catholic.cls032690125.oop3team.auth.dao.AuthManager;

public class SessionController {
    private static Map<String, Session> sessionMap = new HashMap<>();
    private static final int SESSION_TIMEOUT_MINUTES = 60; // 1시간

    // 로그인 성공 시 세션 생성
    public static Session createSession(String userId) {
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
        AuthManager authManager = new AuthManager();
        authManager.insertSession(session);

        return session;
    }

    // 세션 만료 체크 및 갱신
    public static boolean isSessionActive(String userId) {
        Session session = sessionMap.get(userId);
        if (session == null) return false;
        if (session.getExpiredAt().isBefore(LocalDateTime.now())) {
            session.setActive(false);
            return false;
        }
        return session.isActive();
    }

    // 세션 갱신 (활동 시 만료 시간 연장)
    public static void refreshSession(String userId) {
        Session session = sessionMap.get(userId);
        if (session != null && session.isActive()) {
            session.setExpiredAt(LocalDateTime.now().plusMinutes(SESSION_TIMEOUT_MINUTES));
        }
    }

    // 로그아웃 또는 만료 시 세션 삭제
    public static void removeSession(String userId) {
        sessionMap.remove(userId);
        // DB에서도 세션 삭제
        AuthManager authManager = new AuthManager();
        authManager.removeSessionFromDB(userId);
    }

}
