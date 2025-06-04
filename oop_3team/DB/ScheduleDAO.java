package DB;

import gui.schedule.ScheduleScreen.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/schedule_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Vireak2002!!";

    public static List<Schedule> getAllSchedules() {
        List<Schedule> scheduleList = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT * FROM schedule";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String memo = rs.getString("memo");
                scheduleList.add(new Schedule(title, date, time, memo));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduleList;
    }
}
