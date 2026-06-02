package com.mirocoder.habittracker.service;

import com.mirocoder.habittracker.dto.HabitRequest;
import com.mirocoder.habittracker.dto.StatsSummaryResponse;
import com.mirocoder.habittracker.model.Habit;
import com.mirocoder.habittracker.model.HabitStats;
import com.mirocoder.habittracker.repository.DailyStatsRepository;
import com.mirocoder.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;
import com.mirocoder.habittracker.model.DailyStats;

import java.time.LocalDate;
import java.util.List;
import com.mirocoder.habittracker.repository.AppSettingsRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final AppSettingsRepository appSettingsRepository;
    private final DailyStatsRepository dailyStatsRepository;

    public HabitService(HabitRepository habitRepository,
                        AppSettingsRepository appSettingsRepository, DailyStatsRepository dailyStatsRepository) {
        this.habitRepository = habitRepository;
        this.appSettingsRepository = appSettingsRepository;
        this.dailyStatsRepository = dailyStatsRepository;
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

//        int completed = calculateCompletion(habits);
        int completed = habitRepository.countCompleted();
//        int total = habits.size();
        int total = habitRepository.countAll();
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

    public int resetDailyProgressIfNeeded() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime todayNoon = now.toLocalDate().atTime(12, 0);
        LocalDateTime lastResetAt = appSettingsRepository.getLastResetAt();

        if ((now.isAfter(todayNoon)  || now.isEqual(todayNoon)) && lastResetAt.isBefore(todayNoon)) {
            int completed = habitRepository.countCompleted();
            int total = habitRepository.countAll();
            int notCompleted = total - completed;
            double percent = total == 0 ? 0 : dayPercent(total, completed);
            String dayType = total == 0 ? "Zero day" : dayType(total, completed);
            DailyStats stats = new DailyStats(
                    0,
                    now.toLocalDate(),
                    total,
                    completed,
                    notCompleted,
                    percent,
                    dayType
            );
            dailyStatsRepository.save(stats);

            int resetCount = habitRepository.resetCompleted();
            appSettingsRepository.updateLastResetAt(now);
            return resetCount;
        } return 0;
    }

    public List<Habit> getNotCompletedHabits(){
        return habitRepository.findNotCompleted();
    }

    public List<DailyStats> getDailyStatsHistory() {
        return dailyStatsRepository.findAll();
    }

    public DailyStats updateDailyStats(LocalDate date, int total, int completed) {
        if (total < 0 || completed < 0 || completed > total){
            throw new IllegalArgumentException("Invalid values for total and completed");
        }
        int notCompleted = total - completed;
        double percent = total == 0 ? 0 : dayPercent(total, completed);
        String dayType = total == 0 ? "Zero day" : dayType(total, completed);

        DailyStats stats = new DailyStats(
                0,
                date,
                total,
                completed,
                notCompleted,
                percent,
                dayType
        );

        dailyStatsRepository.update(stats);

        return stats;
    }

    public StatsSummaryResponse getStatsSummary(int days) {
        List<DailyStats> history =dailyStatsRepository.findLastDays(days);

        if (history.isEmpty()) {
            return new StatsSummaryResponse(0, 0, 0, 0, 0, 0, 0);
        }

        double totalPercent = 0;
        int perfectDays = 0;
        int strongDays = 0;
        int systemDays = 0;
        int recoveryDays = 0;
        int zeroDays = 0;

        for (DailyStats stats : history) {
            totalPercent += stats.getPercent();
            if (stats.getDayType().equals("Perfect day")) {
                perfectDays++;
            } else if (stats.getDayType().equals("Strong day")) {
                strongDays++;
            } else if (stats.getDayType().equals("System day")) {
                systemDays++;
            } else if (stats.getDayType().equals("Recovery day")) {
                recoveryDays++;
            } else if (stats.getDayType().equals("Zero day")) {
                zeroDays++;
            }
        }

        double averagePercent = totalPercent / history.size();

        return new StatsSummaryResponse(
                history.size(),
                averagePercent,
                perfectDays,
                strongDays,
                systemDays,
                recoveryDays,
                zeroDays
        );
    }

    public Habit getNextHabit() {
        return habitRepository.findNextNotCompleted();
    }
}
