package com.mirocoder.habittracker.startup;

import com.mirocoder.habittracker.service.HabitService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DailyResetStartupChecker {

    private final HabitService habitService;

    public DailyResetStartupChecker(HabitService habitService) {
        this.habitService = habitService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkDailyResetOnStartup() {
        int resetCount = habitService.resetDailyProgressIfNeeded();

        if (resetCount > 0) {
            System.out.println("Daily reset completed on startup. Habits reset: " + resetCount);
        } else {
            System.out.println("Daily reset not needed on startup.");
        }
    }
}