package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;

public class Schedule implements Serializable {
    private String title;
    private String date;
    private String time;
    private String memo;

    public Schedule(String title, String date, String time, String memo) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.memo = memo;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getMemo() { return memo; }
}
