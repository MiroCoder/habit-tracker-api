package com.mirocoder.habittracker.model;

public class Habit {
    private String name;
    private boolean completed;
    private Priority priority;

    public Habit() {

    }

    public Habit(String name, boolean completed, Priority priority) {
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

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}