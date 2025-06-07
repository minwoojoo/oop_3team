package kr.ac.catholic.cls032690125.oop3team.features.chatroom.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.Chatroom;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.StandardDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatroomDAO extends StandardDAO {

    public ChatroomDAO(Server server) {
        super(server);
    }

    /**
     * 새 대화방을 생성하고, 생성된 Chatroom 객체를 리턴합니다. 실패 시 null 리턴.
     */
    public Chatroom createChatroom(String title, String ownerId, Integer parentRoomId) throws SQLException {
        String sql = "INSERT INTO chatroom (title, parentroom_id, closed, is_private) " +
                "VALUES (?, ?, FALSE, FALSE)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);

            if (parentRoomId != null) {
                ps.setInt(2, parentRoomId);
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            int affected = ps.executeUpdate();
            if (affected == 0) {
                return null;
            }

            // 생성된 방 ID를 받아온다
            int newId;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                    // 방 생성 시각은 DB의 default CURRENT_TIMESTAMP로 채워졌으니, 다시 한 번 조회해서 Chatroom 객체를 완성한다.
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

    public Chatroom createChatroomWithParticipants(String title, String ownerId, List<String> participants, Integer parentRoomId, boolean isPrivate) throws SQLException {
        String roomSql = "INSERT INTO chatroom (title, parentroom_id, closed, is_private) " +
                "VALUES (?, ?, FALSE, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement psRoom = conn.prepareStatement(roomSql, Statement.RETURN_GENERATED_KEYS)) {

            psRoom.setString(1, title);
            if (parentRoomId != null) {
                psRoom.setInt(2, parentRoomId);
            } else {
                psRoom.setNull(2, Types.INTEGER);
            }

            psRoom.setBoolean(3, isPrivate);

            int affected = psRoom.executeUpdate();
            if (affected == 0) {
                return null;
            }

            // 2) 방 생성 후, 생성된 chatroom_id를 꺼낸다
            int newChatroomId;
            try (ResultSet rs = psRoom.getGeneratedKeys()) {
                if (rs.next()) {
                    newChatroomId = rs.getInt(1);
                } else {
                    return null;
                }
            }

            // 3) CHATROOM_PARTICIPANT 테이블에 방장(ownerId) 추가
            insertParticipants(conn, newChatroomId, ownerId);

            // 4) participants 리스트(초대된 친구들)가 있으면 순회하면서 삽입
            if (participants != null) {
                for (String userId : participants) {
                    insertParticipants(conn, newChatroomId, userId);
                }
            }

            // 5) 완성된 Chatroom 정보를 DB에서 다시 조회하여 객체로 만들어 반환
            return findById(newChatroomId);
        }
    }

    public void insertParticipants(Connection conn, int chatroomId, String userId) throws SQLException {
        String sql = "INSERT INTO chatroom_participant(chatroom_id, user_id) VALUES(?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            ps.setString(2, userId);
            ps.executeUpdate();
        }
    }

    /**
     * 특정 chatroomId에 속한 모든 user_id를 String 리스트로 반환.
     * @param chatroomId 대상 채팅방 ID
     * @return 해당 방의 참가자 user_id 목록 (없으면 빈 리스트)
     */
    public ArrayList<String> getMemberList(int chatroomId) throws SQLException {
        String sql = "SELECT user_id FROM chatroom_participant WHERE chatroom_id = ?";
        ArrayList<String> members = new ArrayList<>();

        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, chatroomId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    members.add(rs.getString("user_id"));
                }
            }
        }

        return members;
    }

    /**
     * parentroom_id가 특정 값인 모든 스레드(하위 채팅방)를 조회하여 Chatroom 리스트로 반환
     */
    public ArrayList<Chatroom> findThreadsByParentId(int parentId, boolean isOpened) throws SQLException {
        String sql = "SELECT chatroom_id, parentroom_id,closed, title, created_at " +
                "FROM chatroom " +
                "WHERE parentroom_id = ? " +
                "WHERE closed = ? " +
                "ORDER BY created_at DESC";

        ArrayList<Chatroom> threads = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            ps.setBoolean(2, isOpened);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int chatId = rs.getInt("chatroom_id");
                    Integer prId = rs.getObject("parentroom_id") == null
                            ? null
                            : rs.getInt("parentroom_id");
                    String title = rs.getString("title");
                    LocalDateTime created = rs.getTimestamp("created_at").toLocalDateTime();

                    Chatroom c = new Chatroom();
                    c.setChatroomId(chatId);
                    c.setParentroomId(prId);
                    c.setTitle(title);
                    c.setCreated(created);
                    c.setClosed(isOpened);

                    threads.add(c);
                }
            }
        }

        return threads;
    }

    public int closeChatroom(int chatroomId) throws SQLException {
        String sql = "UPDATE chatroom SET closed = TRUE WHERE chatroom_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            return ps.executeUpdate();
        }
    }

    public Chatroom findOrCreatePrivateChatRoom(String userA, String userB) throws SQLException {
        String[] duo = new String[]{userA, userB};
        Arrays.sort(duo);

        String checkSql =
                "SELECT c.chatroom_id " +
                        "  FROM chatroom c " +
                        " JOIN chatroom_participant p1 ON c.chatroom_id = p1.chatroom_id AND p1.user_id = ? " +
                        " JOIN chatroom_participant p2 ON c.chatroom_id = p2.chatroom_id AND p2.user_id = ? " +
                        " WHERE c.is_private = TRUE ";

        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(checkSql);

            ps.setString(1, duo[0]);
            ps.setString(2, duo[1]);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return findById(rs.getInt("chatroom_id"));
                }
            }
        }

        String insertRoomSql = "INSERT INTO chatroom (title, parentroom_id, closed, is_private) VALUES (?, NULL, FALSE, TRUE)";
        int newRoomId;

        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(insertRoomSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, duo[0] + "," + duo[1] + " 1:1 대화");
            ps.executeUpdate();

            try (ResultSet gk = ps.getGeneratedKeys()) {
                gk.next();
                newRoomId = gk.getInt(1);
            }
        }

        String partSql = "INSERT INTO chatroom_participant (chatroom_id, user_id) VALUES (?, ?), (?, ?)";
        try (Connection conn = database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(partSql);
            ps.setInt(1, newRoomId);
            ps.setString(2, duo[0]);
            ps.setInt(3, newRoomId);
            ps.setString(4, duo[1]);
            ps.executeUpdate();
            return findById(newRoomId);
        }
    }

}
