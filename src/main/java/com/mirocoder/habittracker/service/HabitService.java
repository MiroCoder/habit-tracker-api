package com.mirocoder.habittracker.service;

import com.mirocoder.habittracker.dto.HabitRequest;
import com.mirocoder.habittracker.model.Habit;
import com.mirocoder.habittracker.model.HabitStats;
import com.mirocoder.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
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

    public Habit findById(long id) {
        return habitRepository.findById(id);
    }

    public Habit markCompleted(long id) {
        Habit habit = habitRepository.findById(id);

        if (habit == null) {
            return null;
        }

        habit.setCompleted(true);
        return habitRepository.update(habit);
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

    public Habit addHabit(HabitRequest request) {
        Habit habit = new Habit(
                0,
                request.getName(),
                request.isCompleted(),
                request.getPriority()
        );

        return habitRepository.save(habit);
    }

    public HabitStats getStats() {
        List<Habit> habits = habitRepository.findAll();

        int completed = calculateCompletion(habits);
        int total = habits.size();
        int notCompleted = total - completed;
        double percent = total == 0 ? 0 : dayPercent(total, completed);
        String dayType = total == 0 ? "Zero day" : dayType(total, completed);

        return new HabitStats(total, completed, notCompleted, percent, dayType);
    }

    public Habit updateHabit(long id, Habit updatedHabit) {
        Habit habit = habitRepository.findById(id);

        if (habit == null) {
            return null;
        }

        habit.setName(updatedHabit.getName());
        habit.setCompleted(updatedHabit.isCompleted());
        habit.setPriority(updatedHabit.getPriority());

        return habitRepository.update(habit);
    }

    public boolean deleteHabit(long id) {
        return habitRepository.deleteById(id);
    }

    public Habit findByName(String name) {
        return habitRepository.findByName(name);
    }

    public List<Habit> getHabitsByPriority(Habit.Priority priority) {
        return habitRepository.findByPriority(priority);
    }
}
