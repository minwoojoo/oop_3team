package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 45772003L;

    private int scheduleId;
    private int chatroomId;

    private String title;
    private String date;
    private String time;
    private String memo;

    public Schedule(int scheduleId, int chatroomId, String title, String date, String time, String memo) {
        this.scheduleId = scheduleId;
        this.chatroomId = chatroomId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.memo = memo;
    }

    public int getScheduleId() { return scheduleId; }
    public int getChatroomId() { return chatroomId; }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getMemo() { return memo; }
}
