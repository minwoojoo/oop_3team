package auth.dao;

import auth.model.Session;
import config.DBConfig;
import java.sql.*;

public class AuthManager {
    // 아이디로 사용자 존재 여부 확인
    public boolean isIdDuplicate(String userId) {
        String sql = "SELECT 1 FROM user WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // 에러 시 중복으로 간주
        }
    }

    // 사용자 정보 저장
    public boolean insertUser(String userId, String name, String passwordHash) {
        String insertSql = "INSERT INTO user (user_id, name, password_hash) VALUES (?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            insertStmt.setString(1, userId);
            insertStmt.setString(2, name);
            insertStmt.setString(3, passwordHash);
            insertStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인 체크
    public boolean checkLogin(String userId, String passwordHash) {
        String sql = "SELECT 1 FROM user WHERE user_id = ? AND password_hash = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, passwordHash);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 세션 저장
    public boolean insertSession(Session session) {
        String sql = "INSERT INTO session (session_id, user_id, created_at, expired_at, is_active) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, session.getSessionId());
            pstmt.setString(2, session.getUserId());
            pstmt.setObject(3, session.getCreatedAt());
            pstmt.setObject(4, session.getExpiredAt());
            pstmt.setBoolean(5, session.isActive());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    //세션삭제
    public void removeSessionFromDB(String userId) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM session WHERE user_id = ?")) {
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



