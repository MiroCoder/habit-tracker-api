package com.mirocoder.habittracker.startup;

import com.mirocoder.habittracker.service.HabitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DailyResetStartupChecker {

    private static final Logger log = LoggerFactory.getLogger(DailyResetStartupChecker.class);

    private final HabitService habitService;

    public DailyResetStartupChecker(HabitService habitService) {
        this.habitService = habitService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkDailyResetOnStartup() {
        int resetCount = habitService.resetDailyProgressIfNeeded();

        if (resetCount > 0) {
            log.info("Daily reset completed on startup. Habits reset: {}", resetCount);
        } else {
            log.info("Daily reset not needed on startup.");
        }
    }
}
