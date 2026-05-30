package com.mirocoder.habittracker.model;

import java.time.LocalDate;

public class DailyStats {
    private long id;
    private LocalDate statDate;
    private int total;
    private int completed;
    private int notCompleted;
    private double percent;
    private String dayType;

    public DailyStats(){

    }

    public DailyStats(long id, LocalDate statDate, int total, int completed, int notCompleted, double percent, String dayType) {
        this.id = id;
        this.statDate = statDate;
        this.total = total;
        this.completed = completed;
        this.notCompleted = notCompleted;
        this.percent = percent;
        this.dayType = dayType;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStatDate() {
        return statDate;
    }

    public int getTotal() {
        return total;
    }

    public int getCompleted() {
        return completed;
    }

    public int getNotCompleted() {
        return notCompleted;
    }

    public double getPercent() {
        return percent;
    }

    public String getDayType() {
        return dayType;
    }
}
