package com.mirocoder.habittracker.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Habit {
    private long id;
    private boolean required_today;

    @NotBlank(message = "Habit name cannot be empty")
    private String name;
    private boolean completed;

    @NotNull(message = "Priority is required")
    private Priority priority;

    public Habit() {

    }

    public Habit(long id,String name, boolean completed, Priority priority, boolean required_today) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.priority = priority;
        this.required_today = required_today;
    }

    public enum Priority {
        High,
        Medium,
        Low
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public long getId() { return id;}
    public void setId(long id) {this.id = id;}

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isRequiredToday() { return required_today;}
    public void setRequiredToday(boolean required_today) {this.required_today = required_today;}


}