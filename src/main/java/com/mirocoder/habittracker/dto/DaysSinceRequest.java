package com.mirocoder.habittracker.dto;

import java.time.LocalDate;

public class DaysSinceRequest {
    private String name;
    private LocalDate startDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}