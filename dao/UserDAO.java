package dao;

import config.DBConfig;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean checkUserExists(String username) {
        String sql = "SELECT 1 FROM user WHERE name = ? LIMIT 1";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); //user 존재하면 true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserNotExists(int id) {
        String sql = "SELECT 1 FROM user WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1,id);
            ResultSet  rs=stmt.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // user 저장
    public boolean saveUser(User user) {
        if (checkUserExists(user.getUserName())) {
            System.out.println("user 존재");
            return false;
        }

        String sql = "INSERT INTO user (name, password_hash, is_online, work_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPasswordHash());
            stmt.setBoolean(3, user.isOnline());
            stmt.setString(4, user.getWorkStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return false;

            // 만들어진 id로 user 업데이드
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getInt(1));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // id로 user 찾기
    public User getUserById(int id) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByName(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    //  user 수정
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET name = ?, password_hash = ?, is_online = ?, work_status = ? WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPasswordHash());
            stmt.setBoolean(3, user.isOnline());
            stmt.setString(4, user.getWorkStatus());
            stmt.setInt(5, user.getUserId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ID로 삭제
    public void deleteUser(int id) {
        if (!isUserNotExists(id)){
            System.out.println("user not exists");
            return;
        }
        String sql = "DELETE FROM user WHERE user_id = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("UserDAO.deleteUser");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ResultSet -> User
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUserName(rs.getString("name"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setOnline(rs.getBoolean("is_online"));
        user.setWorkStatus(rs.getString("work_status"));

        //  từ Timestamp -> LocalDate
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");
        if (created != null) user.setCreatedAt(created.toLocalDateTime().toLocalDate());
        if (updated != null) user.setUpdatedAt(updated.toLocalDateTime().toLocalDate());

        return user;
    }
}
