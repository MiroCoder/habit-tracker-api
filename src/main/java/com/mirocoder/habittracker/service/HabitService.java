package com.mirocoder.habittracker.service;

import com.mirocoder.habittracker.model.Habit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {

    private final List<Habit> habits = new ArrayList<>();

    public HabitService() {
        habits.add(new Habit("Code", true, Habit.Priority.High));
        habits.add(new Habit("German", false, Habit.Priority.Medium));
        habits.add(new Habit("Stretch", false, Habit.Priority.Low));
    }

    public List<Habit> getAllHabits() {
        return habits;
    }

    public static int calculateCompletion(List<Habit> habits) {
        int counter = 0;

        for (Habit h : habits) {
            if (h.isCompleted()) {
                counter++;
            }
        }

        return counter;
    }

    public static Habit findHabit(List<Habit> habits, String name) {
        for (Habit h : habits) {
            if (h.getName().equalsIgnoreCase(name)) {
                return h;
            }
        }

        return null;
    }

    public static String dayType(int habitsAmount, int habitsDone) {
        double percent = (habitsDone * 100.0) / habitsAmount;

        if (percent == 100.0) {
            return "Perfect day";
        } else if (percent >= 70) {
            return "Strong day";
        } else if (percent >= 50) {
            return "System day";
        } else if (percent > 0) {
            return "Recovery day";
        } else {
            return "Zero day";
        }
    }

    public static double dayPercent(int totalHabits, int completedHabits) {
        return (completedHabits * 100.0) / totalHabits;
    }

    public Habit addHabit(Habit habit) {
        habits.add(habit);
        return habit;
    }
}