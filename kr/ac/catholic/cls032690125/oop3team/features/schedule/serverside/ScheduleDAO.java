package kr.ac.catholic.cls032690125.oop3team.features.schedule.serverside;

import kr.ac.catholic.cls032690125.oop3team.models.Schedule;
import kr.ac.catholic.cls032690125.oop3team.server.Server;
import kr.ac.catholic.cls032690125.oop3team.server.structs.StandardDAO;

public class ScheduleDAO extends StandardDAO {
    public ScheduleDAO(Server server) {
        super(server);
    }

    public int createSchedule(Schedule schedule) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Schedule[] getSchedules() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
