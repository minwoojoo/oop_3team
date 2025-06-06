package kr.ac.catholic.cls032690125.oop3team.features.chatroom.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.StandardDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatroomDAO extends StandardDAO {

    public ChatroomDAO(Server server) {
        super(server);
    }

    /**
     * 새 대화방을 생성하고, 생성된 Chatroom 객체를 리턴합니다. 실패 시 null 리턴.
     */
    public Chatroom createChatroom(String title, String ownerId) throws SQLException {
        String sql = "INSERT INTO chatroom (title, parentroom_id, closed, is_private) VALUES (?, ?, FALSE, FALSE)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setNull(2, Types.INTEGER);  // <-- gán parentroom_id = NULL đúng cách

            int affected = ps.executeUpdate();
            if (affected == 0) {
                return null;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    return findById(newId);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * DB에서 chatroom_id로 대화방 정보를 조회하여 Chatroom 모델을 만들어 리턴합니다.
     */
    public Chatroom findById(int chatroomId) throws SQLException {
        String sql = "SELECT * FROM chatroom WHERE chatroom_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Chatroom room = new Chatroom();
                    room.setChatroomId(rs.getInt("chatroom_id"));
                    room.setParentroomId(rs.getInt("parentroom_id"));
                    room.setClosed(rs.getBoolean("closed"));
                    room.setPrivate(rs.getBoolean("is_private"));
                    room.setTitle(rs.getString("title"));
                    room.setCreated(rs.getTimestamp("created_at").toLocalDateTime());
                    return room;
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * 모든 대화방 목록을 불러와 Chatroom 배열로 리턴합니다.
     */
    public Chatroom[] loadAllChatrooms(boolean isprivate) throws SQLException {
        String sql = "SELECT * FROM chatroom WHERE is_private=? AND parentroom_id IS NULL ORDER BY created_at DESC";
        List<Chatroom> list = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setBoolean(1, isprivate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chatroom room = new Chatroom();
                room.setChatroomId(rs.getInt("chatroom_id"));
                room.setParentroomId(rs.getInt("parentroom_id"));
                room.setClosed(rs.getBoolean("closed"));
                room.setPrivate(rs.getBoolean("is_private"));
                room.setTitle(rs.getString("title"));
                room.setCreated(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(room);
            }
        }
        return list.toArray(new Chatroom[0]);
    }

    public boolean createChatroomWithParticipants(String title, String ownerId, List<String> userIds) throws SQLException {
        Chatroom chatroom = createChatroom(title, ownerId);
        if (chatroom != null) {
            return insertParticipants(chatroom.getChatroomId(), userIds);
        }
        return false;
    }

    public boolean insertParticipants(int chatroomId, List<String> userIds) throws SQLException {
        String sql = "INSERT INTO CHATROOM_PARTICIPANT (chatroom_id, user_id) VALUES (?,?)";

        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String userId : userIds) {
                ps.setInt(1, chatroomId);       // chatroom_id는 INT
                ps.setString(2, userId);        // user_id는 VARCHAR (String)
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
