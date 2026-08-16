package com.mirocoder.habittracker.dto;

import jakarta.validation.constraints.Min;

public class DailyStatsUpdateRequest {
    @Min(value = 0, message = "Total cannot be negative")
    private int total;

    @Min(value = 0, message = "Completed cannot be negative")
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
