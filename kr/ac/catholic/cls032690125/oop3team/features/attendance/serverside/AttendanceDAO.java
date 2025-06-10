package kr.ac.catholic.cls032690125.oop3team.features.attendance.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.Attendance;
import kr.ac.catholic.cls032690125.oop3team.models.AttendanceEditRequest;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.StandardDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO extends StandardDAO {

    public AttendanceDAO(Server server) {
        super(server);
    }

    //  출근
    public void checkIn(String userId, int chatroomId) throws SQLException {
        String sql = "INSERT INTO attendance (user_id,chatroom_id, check_in_time) VALUES (?, ?, NOW())";

        try (PreparedStatement stmt = database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setInt(2, chatroomId);
            stmt.execute();
        }
    }

    //    퇴근 및 근무시간 계산
    public void checkOut(String userId, int chatroomId) throws SQLException {
        String checkSql = """
                    SELECT id FROM attendance 
                    WHERE user_id = ? AND chatroom_id = ? AND check_out_time IS NULL
                    ORDER BY check_in_time DESC
                    LIMIT 1
                """;
        try (PreparedStatement checkStmt = database.getConnection().prepareStatement(checkSql)) {
            checkStmt.setString(1, userId);
            checkStmt.setInt(2, chatroomId);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new SQLException("먼저 출근버튼을 눌러주세요.");
            }
        }

        String updateSql = """
                    UPDATE attendance
                    SET check_out_time = NOW(),
                        work_time_total = TIMESTAMPDIFF(MINUTE, check_in_time, NOW())
                    WHERE user_id = ? AND chatroom_id = ? AND check_out_time IS NULL
                    ORDER BY check_in_time DESC
                    LIMIT 1
                """;
        try (PreparedStatement stmt = database.getConnection().prepareStatement(updateSql)) {
            stmt.setString(1, userId);
            stmt.setInt(2, chatroomId);
            stmt.executeUpdate();
        }


    }


    public List<Attendance> getAttendanceByChatroomId(int chatroomId) throws SQLException {
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = """
                SELECT a.*, u.name
                FROM attendance a
                JOIN user u ON a.user_id = u.user_id
                WHERE a.chatroom_id = ?
                ORDER BY a.check_in_time DESC
                """;
        try (PreparedStatement stmt = database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, chatroomId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("check_in_time"),
                        rs.getTimestamp("check_out_time"),
                        rs.getInt("work_time_total"),
                        rs.getString("name")
                );
                attendanceList.add(attendance);
            }
        }
        return attendanceList;
    }

    public boolean hasAttendanceOnDate(String userId, String date) throws SQLException {
        String sql = """
                    SELECT COUNT(*) FROM attendance 
                    WHERE user_id = ? 
                      AND check_in_time >= ? 
                      AND check_in_time < ?
                """;

        LocalDate localDate = LocalDate.parse(date);  // Ensure "yyyy-MM-dd" format
        Timestamp startOfDay = Timestamp.valueOf(localDate.atStartOfDay());
        Timestamp startOfNextDay = Timestamp.valueOf(localDate.plusDays(1).atStartOfDay());

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setTimestamp(2, startOfDay);
            stmt.setTimestamp(3, startOfNextDay);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }


    public void submitEditRequest(String userId, String date, String checkIn, String checkOut, String reason)
            throws SQLException {
        String sql = """
                    INSERT INTO AttendanceEditRequest 
                    (user_id, attendance_date, requested_check_in, requested_check_out, reason) 
                    VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setTime(3, java.sql.Time.valueOf(checkIn + ":00"));
            stmt.setTime(4, java.sql.Time.valueOf(checkOut + ":00"));
            stmt.setString(5, reason);
            stmt.executeUpdate();
        }
    }

    public List<AttendanceEditRequest> getEditRequestsByUser(String userId) throws SQLException {
        List<AttendanceEditRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM AttendanceEditRequest WHERE user_id = ? ORDER BY requested_at DESC";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AttendanceEditRequest r = new AttendanceEditRequest(
                        rs.getLong("id"),
                        rs.getString("user_id"),
                        rs.getTimestamp("attendance_date"),
                        rs.getString("requested_check_in"),
                        rs.getString("requested_check_out"),
                        rs.getString("reason"),
                        rs.getTimestamp("requested_at"),
                        rs.getString("status")
                );
                requests.add(r);
            }
        }
        return requests;
    }


    public void updateEditRequestStatus(long requestId, String newStatus) throws SQLException {
        String sql = "UPDATE AttendanceEditRequest SET status = ? WHERE id = ?";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setLong(2, requestId);
            stmt.executeUpdate();
        }
    }

    public void approveEditRequest(long editRequestId, String managerId, boolean approved, String rejectReason) throws SQLException {
        String status = approved ? "APPROVED" : "REJECTED";

        String sql = """
                    UPDATE AttendanceEditRequest
                    SET status = ?, 
                        manager_id = ?, 
                        approved_at = NOW(), 
                        reject_reason = ?
                    WHERE id = ?
                """;

        try (PreparedStatement stmt = database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, managerId);
            stmt.setString(3, rejectReason);
            stmt.setLong(4, editRequestId);

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new SQLException("해당 요청 ID를 찾을 수 없습니다: " + editRequestId);
            }
        }
    }

}
