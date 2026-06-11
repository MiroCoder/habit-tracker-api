package com.mirocoder.habittracker.dto;

public class HabitStreakResponse {

    private long habitId;
    private int currentStreak;

    public HabitStreakResponse(long habitId, int currentStreak) {
        this.habitId = habitId;
        this.currentStreak = currentStreak;
    }

    public long getHabitId() {
        return habitId;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }
}