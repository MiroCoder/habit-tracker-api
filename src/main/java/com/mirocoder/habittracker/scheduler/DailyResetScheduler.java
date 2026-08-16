package com.mirocoder.habittracker.scheduler;

import com.mirocoder.habittracker.service.HabitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyResetScheduler {

    private static final Logger log = LoggerFactory.getLogger(DailyResetScheduler.class);

    private final HabitService habitService;

    public DailyResetScheduler(HabitService habitService) {
        this.habitService = habitService;
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "Europe/Warsaw")
    public void resetHabitsAtNoon() {
        int resetCount = habitService.resetDailyProgressIfNeeded();
        log.info("Daily reset completed. Habits reset: {}", resetCount);
    }
}
