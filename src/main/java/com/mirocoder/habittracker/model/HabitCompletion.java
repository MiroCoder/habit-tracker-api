package com.mirocoder.habittracker.model;

import java.time.LocalDate;

public class HabitCompletion {

    private Long id;
    private Long habitId;
    private LocalDate completionDate;

    public HabitCompletion() {}

    public HabitCompletion(Long id, Long habitId, LocalDate completionDate) {
        this.id = id;
        this.habitId = habitId;
        this.completionDate = completionDate;
    }

    public HabitCompletion(Long habitId, LocalDate completionDate) {
        this.habitId = habitId;
        this.completionDate = completionDate;
    }

    public Long getId() { return id; }
    public Long getHabitId() { return habitId; }
    public LocalDate getCompletionDate() { return completionDate; }
}