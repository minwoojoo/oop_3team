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
    public Chatroom createChatroom(String title, String ownerId, Integer parentRoomId) throws SQLException {
        String sql = "INSERT INTO chatroom (title, parentroom_id, closed, is_private, leader_id) VALUES (?, ?, FALSE, FALSE, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, title);

            if (parentRoomId != null) {
                ps.setInt(2, parentRoomId);
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            ps.setString(3, ownerId);  // Đảm bảo ownerId được truyền đúng vào leader_id

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
                    LocalDateTime createdDateTime = rs.getTimestamp("created_at").toLocalDateTime();
                    room.setCreated(createdDateTime);
                    room.setOwnerId(rs.getString("leader_id"));
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
                LocalDateTime createdDateTime = rs.getTimestamp("created_at").toLocalDateTime();
                room.setCreated(createdDateTime);
                room.setOwnerId(rs.getString("leader_id"));
                list.add(room);
            }
        }
        return list.toArray(new Chatroom[0]);
    }

    public Chatroom createChatroomWithParticipants(
            String title,
            String ownerId,
            List<String> participants,
            Integer parentRoomId,
            boolean isPrivate
    ) throws SQLException {
        String roomSql = "INSERT INTO chatroom (title, parentroom_id, closed, is_private, leader_id) VALUES (?, ?, FALSE, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement psRoom = conn.prepareStatement(roomSql, Statement.RETURN_GENERATED_KEYS)) {

            psRoom.setString(1, title);
            if (parentRoomId != null) {
                psRoom.setInt(2, parentRoomId);
            } else {
                psRoom.setNull(2, Types.INTEGER);
            }
            psRoom.setBoolean(3, isPrivate);
            psRoom.setString(4, ownerId);

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
                    if (!userId.equals(ownerId)) {
                        insertParticipants(conn, newChatroomId, userId);
                    }
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
     * 특정 chatroomId에 속한 모든 user_id와 이름을 반환.
     * @param chatroomId 대상 채팅방 ID
     * @return 해당 방의 참가자 user_id, name 목록 (없으면 빈 리스트)
     */
    public List<MemberInfo> getMemberIdNameList(int chatroomId) throws SQLException {
        String sql = "SELECT cp.user_id, u.name FROM chatroom_participant cp LEFT JOIN user u ON cp.user_id = u.user_id WHERE cp.chatroom_id = ?";
        List<MemberInfo> members = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String userId = rs.getString("user_id");
                    String name = rs.getString("name");
                    members.add(new MemberInfo(userId, name));
                }
            }
        }
        return members;
    }

    // 멤버 id, 이름을 담는 내부 클래스
    public static class MemberInfo {
        public final String userId;
        public final String name;
        public MemberInfo(String userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    }

    /**
     * 특정 부모 채팅방의 스레드 목록을 조회하여 Chatroom 리스트로 반환
     * @param parentId 부모 채팅방 ID
     * @param isClosed 종료된 스레드 포함 여부
     * @return 스레드 목록
     */
    public ArrayList<Chatroom> findThreadsByParentId(int parentId, boolean isClosed) throws SQLException {
        String sql = "SELECT * FROM chatroom WHERE parentroom_id = ? AND closed = ? ORDER BY created_at DESC";
        ArrayList<Chatroom> threads = new ArrayList<>();

        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            ps.setBoolean(2, isClosed);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chatroom thread = new Chatroom();
                    thread.setChatroomId(rs.getInt("chatroom_id"));
                    thread.setParentroomId(rs.getInt("parentroom_id"));
                    thread.setClosed(rs.getBoolean("closed"));
                    thread.setPrivate(rs.getBoolean("is_private"));
                    thread.setTitle(rs.getString("title"));
                    LocalDateTime createdDateTime = rs.getTimestamp("created_at").toLocalDateTime();
                    thread.setCreated(createdDateTime);
                    thread.setOwnerId(rs.getString("leader_id"));
                    threads.add(thread);
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

    /**
     * 해당 chatroom_id의 leader_id를 반환
     */
    public String getLeaderId(int chatroomId) throws SQLException {
        String sql = "SELECT leader_id FROM chatroom WHERE chatroom_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("leader_id");
                }
            }
        }
        return null;
    }

    public boolean removeParticipant(int chatroomId, String userId) throws SQLException {
        String sql = "DELETE FROM chatroom_participant WHERE chatroom_id = ? AND user_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            ps.setString(2, userId);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    /**
     * 특정 사용자가 속한 대화방 목록을 가져옵니다.
     * @param userId 사용자 ID
     * @param isPrivate 비공개 대화방 여부
     * @return 사용자가 속한 대화방 목록
     */
    public Chatroom[] loadUserChatrooms(String userId, boolean isPrivate) throws SQLException {
        String sql = "SELECT c.* FROM chatroom c " +
                "INNER JOIN chatroom_participant cp ON c.chatroom_id = cp.chatroom_id " +
                "WHERE cp.user_id = ? AND c.is_private = ? AND c.parentroom_id IS NULL " +
                "ORDER BY c.created_at DESC";
        List<Chatroom> list = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setBoolean(2, isPrivate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chatroom room = new Chatroom();
                room.setChatroomId(rs.getInt("chatroom_id"));
                room.setParentroomId(rs.getInt("parentroom_id"));
                room.setClosed(rs.getBoolean("closed"));
                room.setPrivate(rs.getBoolean("is_private"));
                room.setTitle(rs.getString("title"));
                LocalDateTime createdDateTime = rs.getTimestamp("created_at").toLocalDateTime();
                room.setCreated(createdDateTime);
                room.setOwnerId(rs.getString("leader_id"));
                list.add(room);
            }
        }
        return list.toArray(new Chatroom[0]);
    }

    public Chatroom findOrCreatePrivateChatroom(String userA, String userB) throws SQLException {
        // 1. 두 userId가 모두 참가자인 is_private=1 채팅방이 있는지 SELECT
        String sql = "SELECT c.* FROM chatroom c " +
                "JOIN chatroom_participant cp1 ON c.chatroom_id = cp1.chatroom_id " +
                "JOIN chatroom_participant cp2 ON c.chatroom_id = cp2.chatroom_id " +
                "WHERE c.is_private = 1 AND cp1.user_id = ? AND cp2.user_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userA);
            ps.setString(2, userB);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return findById(rs.getInt("chatroom_id"));
                }
            }
        }
        // 2. 없으면 새로 생성
        List<String> participants = new ArrayList<>();
        participants.add(userA);
        participants.add(userB);
        return createChatroomWithParticipants(userA + "와 " + userB + "의 1:1채팅", userA, participants, null, true);
    }

}