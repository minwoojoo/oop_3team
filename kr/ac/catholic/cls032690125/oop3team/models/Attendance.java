package kr.ac.catholic.cls032690125.oop3team.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Attendance implements Serializable {
    private static final long serialVersionUID = 45772010L;

    private int id;
    private String userId;
    private Timestamp checkInTime;
    private Timestamp checkOutTime;
    private int workTimeTotal;
    private String username;

    public Attendance() {};

    public Attendance(int id, String userId, Timestamp checkInTime, Timestamp checkOutTime, int workTimeTotal,String username) {
        this.id = id;
        this.userId = userId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.workTimeTotal = workTimeTotal;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getCheckInTime() {
        return checkInTime;
    }

    public Timestamp getCheckOutTime() {
        return checkOutTime;
    }

    public int getWorkTimeTotal() {
        return workTimeTotal;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCheckInTime(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void setCheckOutTime(Timestamp checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public void setWorkTimeTotal(int workTimeTotal) {
        this.workTimeTotal = workTimeTotal;
    }
}
