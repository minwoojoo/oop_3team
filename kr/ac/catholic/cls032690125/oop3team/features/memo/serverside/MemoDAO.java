package kr.ac.catholic.cls032690125.oop3team.features.memo.serverside;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.ac.catholic.cls032690125.oop3team.features.setting.clientside.gui.MemoListScreen;
import kr.ac.catholic.cls032690125.oop3team.server.Database;
import kr.ac.catholic.cls032690125.oop3team.models.ChatMemo;

public class MemoDAO {
    private final Database database;

    public MemoDAO(Database database) {
        this.database = database;
    }

    // 메모 저장
    public void saveMemo(String userId, long messageId, String memoText) throws SQLException {
        String sql = "INSERT INTO MEMO (user_id, message_id, memo_text) VALUES (?, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setLong(2, messageId);
            ps.setString(3, memoText);
            ps.executeUpdate();
        }
    }

    // 메모 목록 조회
    public List<ChatMemo> getMemosByUserId(String userId) throws SQLException {
        List<ChatMemo> memos = new ArrayList<>();
        String sql = "SELECT m.created_at, msg.content, m.memo_text FROM MEMO m JOIN MESSAGES msg ON m.message_id = msg.message_id WHERE m.user_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String timestamp = rs.getString("created_at");
                    String chatContent = rs.getString("content");
                    String memo = rs.getString("memo_text");
                    memos.add(new ChatMemo(timestamp, chatContent, memo));
                }
            }
        }
        return memos;
    }

    // 메모 수정
    public boolean updateMemo(String userId, String timestamp, String memoText) throws SQLException {
        String sql = "UPDATE MEMO SET memo_text = ? WHERE user_id = ? AND created_at = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memoText);
            ps.setString(2, userId);
            ps.setString(3, timestamp);
            return ps.executeUpdate() > 0;
        }
    }

    // 메모 삭제
    public boolean deleteMemo(String userId, String timestamp) throws SQLException {
        String sql = "DELETE FROM MEMO WHERE user_id = ? AND created_at = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, timestamp);
            return ps.executeUpdate() > 0;
        }
    }
}
