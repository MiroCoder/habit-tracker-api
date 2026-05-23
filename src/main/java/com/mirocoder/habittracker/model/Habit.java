package com.mirocoder.habittracker.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Habit {
    private long id;

    @NotBlank(message = "Habit name cannot be empty")
    private String name;
    private boolean completed;

    @NotNull(message = "Priority is required")
    private Priority priority;

    public Habit() {

    }

    public Habit(Long id,String name, boolean completed, Priority priority) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.priority = priority;
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


}