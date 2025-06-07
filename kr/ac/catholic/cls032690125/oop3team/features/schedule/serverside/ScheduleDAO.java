package kr.ac.catholic.cls032690125.oop3team.features.schedule.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.Schedule;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.StandardDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO extends StandardDAO {
    public ScheduleDAO(Server server) {
        super(server);
    }

    public int createSchedule(Schedule schedule) {
        String sql = "INSERT INTO schedule (chatroom_id, title, schedule_date, schedule_time, memo) VALUES (?,?,?,?,?)";
        try (Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, schedule.getScheduleId());
            ps.setString(2, schedule.getTitle());
            ps.setString(3, schedule.getDate());
            ps.setString(4, schedule.getTime());
            ps.setString(5, schedule.getMemo());
            ps.executeUpdate();
            int newId;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getInt(1);
                    return newId;
                } else {
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Schedule[] getSchedules(int chatroomId) {
        String sql = "SELECT * FROM schedule WHERE chatroom_id = ?";
        List<Schedule> schedules = new ArrayList<>();
        try (Connection conn = database.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, chatroomId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Schedule sch = new Schedule(
                                rs.getInt("schedule_id"),
                                rs.getInt("chatroom_id"),
                                rs.getString("title"),
                                rs.getString("schedule_date"),
                                rs.getString("schedule_time"),
                                rs.getString("memo")
                        );
                        schedules.add(sch);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return schedules.toArray(new Schedule[schedules.size()]);
        }
    }
}
