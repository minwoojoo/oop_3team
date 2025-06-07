package kr.ac.catholic.cls032690125.oop3team.features.setting.serverside;

import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SettingDAO {
    private final Server server;
    private final Database database;

    public SettingDAO(Server server) {
        this.server = server;
        this.database = server.getDatabase();
    }

    public String getUserName(String userId) {
        String sql = "SELECT name FROM user WHERE user_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                return name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateWorkStatus(String userId, String workStatus) {
        String sql = "UPDATE user SET work_status = ? WHERE user_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, workStatus);
            pstmt.setString(2, userId);
            int updated = pstmt.executeUpdate();
            System.out.println("[SettingDAO] 업데이트된 행 수: " + updated);
            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getWorkStatus(String userId) {
        String sql = "SELECT work_status FROM user WHERE user_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("work_status");
        } catch (Exception e) { e.printStackTrace(); }
        return "";
    }

    public List<String> getBlockedUserNames(String userId) {
        String sql = "SELECT u.name FROM friend f JOIN user u ON f.friend_id = u.user_id WHERE f.user_id = ? AND f.is_blocked = 1";
        List<String> blockedNames = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    blockedNames.add(rs.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blockedNames;
    }

    public boolean unblockUser(String userId, String blockedUserName) {
        String sql = "UPDATE friend f JOIN user u ON f.friend_id = u.user_id " +
                    "SET f.is_blocked = 0 " +
                    "WHERE f.user_id = ? AND u.name = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, blockedUserName);
            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
