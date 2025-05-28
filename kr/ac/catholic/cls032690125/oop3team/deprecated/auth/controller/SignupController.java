package kr.ac.catholic.cls032690125.oop3team.deprecated.auth.controller;

import kr.ac.catholic.cls032690125.oop3team.deprecated.auth.dao.AuthManager;

public class SignupController {
    private AuthManager authManager = new AuthManager();

    // 비밀번호 해시 함수 (AuthManager와 동일하게 맞춰야 함)
    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    // 회원가입 처리
    public boolean signup(String userId, String name, String password) {
        // 1. 아이디 중복 확인
        if (authManager.isIdDuplicate(userId)) {
            return false; // 이미 존재
        }
        // 2. 회원 정보 저장
        return authManager.insertUser(userId, name, hashPassword(password));
    }

    public boolean isIdDuplicate(String userId) {
        return authManager.isIdDuplicate(userId);
    }
}
