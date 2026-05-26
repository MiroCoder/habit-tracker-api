package com.mirocoder.habittracker.scheduler;

import com.mirocoder.habittracker.service.HabitService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyResetScheduler {

    private final HabitService habitService;

    public DailyResetScheduler(HabitService habitService) {
        this.habitService = habitService;
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "Europe/Warsaw")
   // @Scheduled(cron = "0 * * * * *", zone = "Europe/Warsaw")
    public void resetHabitsAtNoon() {
        int resetCount = habitService.resetDailyProgressIfNeeded();
        System.out.println("Daily reset completed. Habits reset: " + resetCount);
    }
}