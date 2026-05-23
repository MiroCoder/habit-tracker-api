package com.mirocoder.habittracker.model;

public class HabitStats {
    private int total;
    private int completed;
    private int notCompleted;
    private double percent;
    private String dayType;

    public HabitStats(int total, int completed, int notCompleted, double percent, String dayType) {
        this.total = total;
        this.completed = completed;
        this.notCompleted = notCompleted;
        this.percent = percent;
        this.dayType = dayType;
    }

    public int getTotal() {return total;}
    public int getCompleted() {return completed;}
    public int getNotCompleted() {return notCompleted;}
    public double getPercent() {return percent;}
    public String getDayType() {return dayType;}
}


