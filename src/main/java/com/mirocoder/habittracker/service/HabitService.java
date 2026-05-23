package com.mirocoder.habittracker.service;

import com.mirocoder.habittracker.model.Habit;
import com.mirocoder.habittracker.model.HabitStats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {

    private final List<Habit> habits = new ArrayList<>();

    private long nextId = 4;

    public HabitService() {
        habits.add(new Habit(1L,"Code", true, Habit.Priority.High));
        habits.add(new Habit(2L,"German", false, Habit.Priority.Medium));
        habits.add(new Habit(3L,"Stretch", false, Habit.Priority.Low));
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

    public Habit findById(Long id) {
        for (Habit h : habits) {
            if(h.getId() == id){
                return h;
            }
        }
        return null;
    }

    public Habit markCompleted(Long id) {
        Habit habit = findById(id);

        if (habit == null) { return null;}

        habit.setCompleted(true);
        return habit;
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
        habit.setId(nextId++);
        habits.add(habit);
        return habit;
    }

    public HabitStats getStats() {
    int completed = calculateCompletion(habits);
    int total = habits.size();
    int notCompleted = total - completed;
    double percent = dayPercent(total, completed);
    String dayType = dayType(total, completed);

    return new HabitStats(total,completed, notCompleted, percent, dayType);
    }

    public Habit updateHabit(Long id, Habit updatedHabit) {
        Habit habit = findById(id);

        if(habit == null) {
            return null;
        }

        habit.setName(updatedHabit.getName());
        habit.setCompleted(updatedHabit.isCompleted());
        habit.setPriority(updatedHabit.getPriority());

        return habit;
    }

    public boolean deleteHabit(Long id) {
        Habit habit = findById(id);

        if(habit == null) {
            return false;
        }

        habits.remove(habit);
        return true;
    }
}