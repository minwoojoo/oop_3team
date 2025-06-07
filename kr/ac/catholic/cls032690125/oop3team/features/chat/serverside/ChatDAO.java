package kr.ac.catholic.cls032690125.oop3team.features.chat.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.Message;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.StandardDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO extends StandardDAO {
    public ChatDAO(Server server) {
        super(server);
    }

    public long insertMessage(Message message) {
        String sql = "INSERT INTO MESSAGES (chatroom_id, sender_id, sent, is_system, content) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn  = database.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setInt(1, message.getChatroomId());
            insertStmt.setString(2, message.getSenderId());
            insertStmt.setObject(3, message.getSent());
            insertStmt.setBoolean(4, message.isSystem());
            insertStmt.setString(5, message.getContent());
            insertStmt.executeUpdate();
            long newId;
            try (ResultSet rs = insertStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getLong(1);
                    return newId;
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Message[] loadMessages(int chatroomId, long refPoint, int period){
        String sqlup = "SELECT * FROM (SELECT * FROM messages WHERE chatroom_id = ? AND message_id < ? ORDER BY message_id DESC LIMIT ?) AS up ORDER BY message_id ASC";
        String sqldown = "SELECT * FROM messages WHERE chatroom_id = ? AND message_id > ? ORDER BY message_id ASC LIMIT ?";
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(period < 0 ? sqlup : sqldown)) {
            pstmt.setInt(1, chatroomId);
            pstmt.setLong(2, refPoint == 0 ? (period < 0 ? Long.MAX_VALUE-1 : 0) : refPoint);
            pstmt.setInt(3, Math.abs(period));
            ResultSet rs = pstmt.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                messages.add(new Message(
                        rs.getLong("message_id"),
                        rs.getInt("chatroom_id"),
                        rs.getString("sender_id"),
                        rs.getString("content"),
                        rs.getBoolean("is_system"),
                        rs.getTimestamp("sent").toLocalDateTime()
                ));
            }
            //return (Message[]) messages.toArray();
            return messages.toArray(new Message[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
