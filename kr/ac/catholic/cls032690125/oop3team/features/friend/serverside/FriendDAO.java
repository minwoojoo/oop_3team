package kr.ac.catholic.cls032690125.oop3team.features.friend.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.responses.UserProfile;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendDAO {
    private final Server server;
    private final Database database;

    public FriendDAO(Server server) {
        this.server = server;
        this.database = server.getDatabase();
    }

    public boolean inviteFriend(String userId, String friendId) throws SQLException {
        String sql = "INSERT INTO friend (user_id, friend_id, is_blocked, is_pending, created_at) VALUES (?, ?, false, true, NOW())";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, friendId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<UserProfile> searchUser(String search) throws SQLException {
        String sql = "SELECT user_id, name, is_online FROM user WHERE user_id = ?";
        List<UserProfile> result = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, search);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String userId = rs.getString("user_id");
                    String name = rs.getString("name");
                    boolean isActive = rs.getInt("is_online") == 1;
                    result.add(new UserProfile(userId, name, isActive));
                }
            }
        }
        return result;
    }

    public List<UserProfile> getPendingFriendRequests(String myUserId) throws SQLException {
        String sql = "SELECT u.user_id, u.name, u.is_online FROM friend f JOIN user u ON f.user_id = u.user_id WHERE f.friend_id = ? AND f.is_pending = 1";
        List<UserProfile> result = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, myUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String userId = rs.getString("user_id");
                    String name = rs.getString("name");
                    boolean isActive = rs.getInt("is_online") == 1;
                    result.add(new UserProfile(userId, name, isActive));
                }
            }
        }
        return result;
    }

    public void acceptFriendRequest(String myUserId, String requesterId) throws SQLException {
        String updateSql = "UPDATE friend SET is_pending = 0 WHERE user_id = ? AND friend_id = ?";
        String insertSql = "INSERT INTO friend (user_id, friend_id, is_blocked, is_pending, created_at) VALUES (?, ?, false, 0, NOW())";
        try (Connection conn = database.getConnection()) {
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, requesterId);
                updateStmt.setString(2, myUserId);
                updateStmt.executeUpdate();
            }
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, myUserId);
                insertStmt.setString(2, requesterId);
                insertStmt.executeUpdate();
            }
        }
    }

    public void deleteFriendRequest(String userId, String requesterId) throws SQLException {
        String sql = "DELETE FROM friend WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";
        
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            pstmt.setString(2, requesterId);
            pstmt.setString(3, requesterId);
            pstmt.setString(4, userId);
            
            int affectedRows = pstmt.executeUpdate();
            
        }
    }

    public boolean isFriend(String userId, String friendId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM friend WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";
        
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            pstmt.setString(2, friendId);
            pstmt.setString(3, friendId);
            pstmt.setString(4, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<UserProfile> getFriendList(String userId) throws SQLException {
        String sql = "SELECT u.user_id, u.name, u.is_online, u.work_status " +
                     "FROM user u JOIN friend f ON u.user_id = f.friend_id " +
                     "WHERE f.user_id = ? AND f.is_pending = 0";
        List<UserProfile> result = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String friendId = rs.getString("user_id");
                    String name = rs.getString("name");
                    boolean isOnline = rs.getInt("is_online") == 1;
                    String workStatus = rs.getString("work_status");
                    result.add(new UserProfile(friendId, name, isOnline, workStatus));
                }
            }
        }
        return result;
    }

    public boolean blockFriend(String userId, String friendId) {
        String sql = "UPDATE friend SET is_blocked = 1 WHERE user_id = ? AND friend_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, friendId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFriend(String userId, String friendId) {
        String sql = "DELETE FROM friend WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, friendId);
            pstmt.setString(3, friendId);
            pstmt.setString(4, userId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBlocked(String userId, String friendId) throws SQLException {
        String sql = "SELECT is_blocked FROM friend WHERE user_id = ? AND friend_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, friendId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_blocked");
                }
            }
        }
        return false;
    }
}
