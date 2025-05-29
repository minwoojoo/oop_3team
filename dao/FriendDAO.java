package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendDAO {
    private final Connection conn;

    public FriendDAO(Connection conn) {
        this.conn = conn;
    }

    //    친구 추가
    public void addFriend(int userID, int friendID) throws SQLException {
        String sql = "INSERT IGNORE INTO friend (user_id, friend_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, friendID);
            stmt.executeUpdate();
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, friendID);
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        }
    }

    //    친구 삭제
    public void removeFriend(int userId, int friendId) throws SQLException {
        String sql = "DELETE FROM friend WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.setInt(3, friendId);
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        }
    }

    public List<User> getFriends(int userId) throws SQLException {
        List<User> friends = new ArrayList<>();
        String sql = """
            SELECT u.user_id, u.name, u.work_status, u.is_online 
            FROM friend f 
            JOIN user u ON f.friend_id = u.user_id 
            WHERE f.user_id = ? AND f.is_pending = FALSE AND f.is_blocked = FALSE
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        "", // password not selected
                        rs.getBoolean("is_online"),
                        rs.getString("work_status")
                );
                friends.add(user);
            }
        }
        return friends;
    }
}
