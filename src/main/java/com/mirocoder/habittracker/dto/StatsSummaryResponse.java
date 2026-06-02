package com.mirocoder.habittracker.dto;

public class StatsSummaryResponse {
    private int days;
    private double averagePercent;
    private int perfectDays;
    private int strongDays;
    private int systemDays;
    private int recoveryDays;
    private int zeroDays;

    public StatsSummaryResponse(int days, double averagePercent, int perfectDays, int strongDays, int systemDays, int recoveryDays, int zeroDays) {
        this.days = days;
        this.averagePercent = averagePercent;
        this.perfectDays = perfectDays;
        this.strongDays = strongDays;
        this.systemDays = systemDays;
        this.recoveryDays = recoveryDays;
        this.zeroDays = zeroDays;
    }
    public int getDays() {
        return days;
    }
    public double getAveragePercent() {
        return averagePercent;
    }
    public int getPerfectDays() {
        return perfectDays;
    }
    public int getStrongDays() {
        return strongDays;
    }
    public int getSystemDays() {
        return systemDays;
    }
    public int getRecoveryDays() {
        return recoveryDays;
    }
    public int getZeroDays() {
        return zeroDays;
    }
}
