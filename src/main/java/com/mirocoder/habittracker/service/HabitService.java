package com.mirocoder.habittracker.service;

import com.mirocoder.habittracker.model.Habit;
import com.mirocoder.habittracker.model.HabitStats;
import org.springframework.stereotype.Service;
import com.mirocoder.habittracker.dto.HabitRequest;
import com.mirocoder.habittracker.repository.HabitRepository;
import com.mirocoder.habittracker.dto.HabitRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {

    private final List<Habit> habits = new ArrayList<>();
    private final HabitRepository habitRepository;
    private long nextId = 4;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;

        habits.add(new Habit(1L,"Code", true, Habit.Priority.High));
        habits.add(new Habit(2L,"German", false, Habit.Priority.Medium));
        habits.add(new Habit(3L,"Stretch", false, Habit.Priority.Low));
    }

//    public List<Habit> getAllHabits() {
//        return habits;
//    }
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

    public static Habit findHabit(List<Habit> habits, String name) {
        for (Habit h : habits) {
            if (h.getName().equalsIgnoreCase(name)) {
                return h;
            }
        }

        return null;
    }

    public Habit findById(long id) {
        for (Habit h : habits) {
            if(h.getId() == id){
                return h;
            }
        }
        return null;
    }

    public Habit markCompleted(long id) {
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

//    public Habit addHabit(HabitRequest request) {
//        Habit habit = new Habit(
//                nextId++,
//                request.getName(),
//                request.isCompleted(),
//                request.getPriority()
//        );
//
//        habits.add(habit);
//        return habit;
//    }

    public Habit addHabit(HabitRequest request) {
        Habit habit = new Habit(
                0,
                request.getName(),
                request.isCompleted(),
                request.getPriority()
        );

        return habitRepository.save(habit);
    }

//    public HabitStats getStats() {
//        int completed = calculateCompletion(habits);
//        int total = habits.size();
//        int notCompleted = total - completed;
//        double percent = dayPercent(total, completed);
//        String dayType = dayType(total, completed);
//
//        return new HabitStats(total,completed, notCompleted, percent, dayType);
//    }
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
        Habit habit = findById(id);

        if(habit == null) {
            return null;
        }

        habit.setName(updatedHabit.getName());
        habit.setCompleted(updatedHabit.isCompleted());
        habit.setPriority(updatedHabit.getPriority());

        return habit;
    }

    public boolean deleteHabit(long id) {
        Habit habit = findById(id);

        if(habit == null) {
            return false;
        }

        habits.remove(habit);
        return true;
    }

    public Habit findByName(String name) {
        for (Habit h: habits) {
            if (h.getName().equals(name)){
                return h;
            }
        }
        return null;
    }

    public List<Habit> getHabitsByPriority(Habit.Priority priority) {
        List<Habit> result = new ArrayList<>();
        for (Habit h: habits) {
            if (h.getPriority().equals(priority)){
                result.add(h);
            }
        }
        return result;
    }
}