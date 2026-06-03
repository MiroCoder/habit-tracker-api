package com.mirocoder.habittracker.model;

import java.time.LocalDate;

public class DaysSince {
    private long id;
    private String name;
    private LocalDate startDate;
    private long daysCount;

    public DaysSince(long id, String name, LocalDate startDate, long daysCount) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.daysCount = daysCount;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
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
    public long getDaysCount() {
        return daysCount;
    }
    public void setDaysCount(long daysCount) {
        this.daysCount = daysCount;
    }
}
