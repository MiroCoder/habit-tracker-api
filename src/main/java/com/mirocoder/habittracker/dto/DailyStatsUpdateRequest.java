package com.mirocoder.habittracker.dto;

public class DailyStatsUpdateRequest {
    private int total;
    private int completed;

    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return total;
    }
    public void setCompleted(int completed){
        this.completed = completed;
    }
    public int getCompleted(){
        return completed;
    }
}
