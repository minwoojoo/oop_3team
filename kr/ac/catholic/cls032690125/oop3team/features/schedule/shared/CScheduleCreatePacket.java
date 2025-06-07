package kr.ac.catholic.cls032690125.oop3team.features.schedule.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Schedule;
import kr.ac.catholic.cls032690125.oop3team.shared.ClientOrderBasePacket;

public class CScheduleCreatePacket extends ClientOrderBasePacket {
    private Schedule schedule;

    public CScheduleCreatePacket(Schedule schedule) {
        super();
        this.schedule = schedule;
    }

    public Schedule getSchedule() { return schedule; }
}
