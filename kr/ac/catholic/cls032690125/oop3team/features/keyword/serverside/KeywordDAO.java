package kr.ac.catholic.cls032690125.oop3team.features.keyword.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.Keyword;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.StandardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class KeywordDAO extends StandardDAO {

    public KeywordDAO(Server server) {
        super(server);
    }

    public boolean addKeyword(String userId,int chatRoomId,String keyword) throws Exception {
        String sql = "INSERT INTO keyword (user_id, chatroom_id, keyword) VALUES (?, ?, ?)";

        try (Connection conn = database.getConnection();){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setInt(2, chatRoomId);
            ps.setString(3, keyword);
            ps.executeUpdate();
            return true;

        }catch (SQLIntegrityConstraintViolationException e){
            return false;
        }
    }

    public boolean deleteKeyword(String userId,int chatRoomId,String keyword) throws Exception {
        String sql = "DELETE FROM keyword WHERE user_id = ? AND chatroom_id = ? AND keyword = ?";

        try (Connection conn = database.getConnection();){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setInt(2, chatRoomId);
            ps.setString(3, keyword);

            return  ps.executeUpdate()>0;
        }
    }

    public List<Keyword> getKeywords(String userId,int chatroomId) throws Exception {
        String sql = "SELECT * FROM keyword WHERE user_id = ? AND chatroom_id = ?";

        List<Keyword> keywords = new ArrayList<>();
        try (Connection conn = database.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setInt(2, chatroomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Keyword kw = new Keyword(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getInt("chatroom_id"),
                        rs.getString("keyword"),
                        rs.getTimestamp("created_at")
                );
                keywords.add(kw);
            }
        }
        return keywords;

    }

    public boolean keywordExists(String userId,int chatroomId,String keyword) throws Exception {
        String sql = "SELECT COUNT(*) FROM keyword WHERE user_id = ? AND chatroom_id = ? AND keyword = ?";
        try (Connection conn = database.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setInt(2, chatroomId);
            ps.setString(3, keyword);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
