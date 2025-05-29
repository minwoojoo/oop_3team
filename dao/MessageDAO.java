package dao;

import config.DBConfig;
import model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    // message 저장
    public boolean saveMessage(Message message) {
        String sql = "INSERT INTO message (chat_room_id, sender_id, receiver_id, content) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, message.getChatRoomID());
            stmt.setInt(2, message.getSenderID());

            if (message.getReceiverID() == 0) {
                stmt.setNull(3, Types.INTEGER); // group chat
            } else {
                stmt.setInt(3, message.getReceiverID());
            }

            stmt.setString(4, message.getContent());

            int rows = stmt.executeUpdate();
            if (rows == 0) return false;

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                message.setMessengerID(rs.getInt(1));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Message getMessageById(int messageId) {
        String sql = "SELECT * FROM message WHERE message_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, messageId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractMessageFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getMessagesByChatRoom(int chatRoomId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message WHERE chat_room_id = ? ORDER BY sent_at ASC";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chatRoomId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                messages.add(extractMessageFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public boolean deleteMessage(int messageId) {
        String sql = "DELETE FROM message WHERE message_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, messageId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Message extractMessageFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("message_id");
        int chatRoomId = rs.getInt("chat_room_id");
        int senderId = rs.getInt("sender_id");
        int receiverId = rs.getInt("receiver_id");
        String content = rs.getString("content");
        Timestamp sentAt = rs.getTimestamp("sent_at");

        return new Message(id, chatRoomId, senderId, receiverId, content, sentAt.toLocalDateTime(), 0);
    }
}
