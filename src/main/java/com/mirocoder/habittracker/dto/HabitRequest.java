package com.mirocoder.habittracker.dto;

import com.mirocoder.habittracker.model.Habit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class HabitRequest {

    @NotBlank(message = "Habit name cannot be empty")
    private String name;

    private boolean completed;
    private boolean required_today;

    @NotNull(message = "Priority is required")
    private Habit.Priority priority;

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Habit.Priority getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setPriority(Habit.Priority priority) {
        this.priority = priority;
    }

    public boolean isRequiredToday() {
        return required_today;
    }

    public void setRequiredToday(boolean required_today) {
        this.required_today = required_today;
    }
}