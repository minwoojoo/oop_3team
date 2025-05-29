package dao;

import config.DBConfig;
import model.ChatRoom;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomDAO {
    public boolean createChatRoom(ChatRoom chatRoom) {
        String sql = "INSERT INTO chatroom (chat_room_name, owner_id) VALUES (?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, chatRoom.getChatRoomName());
            stmt.setInt(2, chatRoom.getOwnerID());

            int rows = stmt.executeUpdate();
            if (rows == 0) return false;

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                chatRoom.setChatRoomID(rs.getInt(1));
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ChatRoom getChatRoomById(int id) {
        String sql = "SELECT * FROM chatroom WHERE chat_room_id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractChatRoomFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ChatRoom> getAllChatRooms() {
        List<ChatRoom> rooms = new ArrayList<>();
        String sql = "SELECT * FROM chatroom";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rooms.add(extractChatRoomFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public boolean deleteChatRoom(int id) {
        String sql = "DELETE FROM chatroom WHERE chat_room_id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ChatRoom extractChatRoomFromResultSet(ResultSet rs) throws SQLException {
        int chatRoomId = rs.getInt("chat_room_id");
        String name = rs.getString("chat_room_name");
        int ownerId = rs.getInt("owner_id");
        Timestamp createdAt = rs.getTimestamp("created_at");

        return new ChatRoom(chatRoomId, name, ownerId, null, null, createdAt.toLocalDateTime().toLocalTime());
    }
}
