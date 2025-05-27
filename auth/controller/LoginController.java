package auth.controller;

import auth.dao.AuthManager;

public class LoginController {
    private AuthManager authManager = new AuthManager();

    // 비밀번호 해시 함수 (AuthManager와 동일하게 맞춰야 함)
    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    // 로그인 처리
    public boolean login(String userId, String password) {
        return authManager.checkLogin(userId, hashPassword(password));
    }
}