package com.mirocoder.habittracker.service;
import com.mirocoder.habittracker.model.Habit;
import com.mirocoder.habittracker.model.DailyStats;
import com.mirocoder.habittracker.repository.AppSettingsRepository;
import com.mirocoder.habittracker.repository.DailyStatsRepository;
import com.mirocoder.habittracker.repository.HabitRepository;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HabitServiceTest {

    @Test
    void calculateCompletionCountsCompletedHabits() {
        ArrayList<Habit> habits = new ArrayList<>();
        habits.add(new Habit(1L,"Code", true, Habit.Priority.High, true));
        habits.add(new Habit(2L,"German", false, Habit.Priority.Medium, false));
        habits.add(new Habit(3L,"Stretch", true, Habit.Priority.Low, false));

        int result = HabitService.calculateCompletion(habits);

        assertEquals(2, result);
    }

    @Test
    void updateDailyStatsCalculatesPerfectDay() {
        HabitRepository habitRepository = mock(HabitRepository.class);
        AppSettingsRepository appSettingsRepository = mock(AppSettingsRepository.class);
        DailyStatsRepository dailyStatsRepository = mock(DailyStatsRepository.class);

        HabitService service = new HabitService(habitRepository, appSettingsRepository, dailyStatsRepository);

        LocalDate date = LocalDate.of(2026, 6, 1);

        DailyStats result = service.updateDailyStats(date, 3, 3);

        assertEquals(date, result.getStatDate());
        assertEquals(3, result.getTotal());
        assertEquals(3, result.getCompleted());
        assertEquals(0, result.getNotCompleted());
        assertEquals(100.0, result.getPercent(), 0.001);
        assertEquals("Perfect day", result.getDayType());

        verify(dailyStatsRepository).update(result);
    }

//    @Test
//    void findHabitFindsByNameIgnoringCase() {
//        ArrayList<Habit> habits = new ArrayList<>();
//        habits.add(new Habit(1L,"Code", false, Habit.Priority.High));
//
//        Habit result = HabitService.findHabit(habits, "code");
//
//        assertNotNull(result);
//        assertEquals("Code", result.getName());
//    }
//
//    @Test
//    void findHabitReturnsNullWhenNotFound() {
//        ArrayList<Habit> habits = new ArrayList<>();
//        habits.add(new Habit(1L,"Code", false, Habit.Priority.High));
//
//        Habit result = HabitService.findHabit(habits, "Sleep");
//
//        assertNull(result);
//    }

    @Test
    void dayTypeReturnsPerfectDayForHundredPercent() {
        String result = HabitService.dayType(3, 3);

        assertEquals("Perfect day", result);
    }

    @Test
    void dayPercentReturnsCorrectPercent() {
        double result = HabitService.dayPercent(4, 2);

        assertEquals(50.0, result, 0.001);
    }
    @Test
    void dayTypeReturnsStrongDayForSeventyPercent() {
        String result = HabitService.dayType(10, 7);

        assertEquals("Strong day", result);
    }

    @Test
    void dayTypeReturnsSystemDayForFiftyPercent() {
        String result = HabitService.dayType(10, 5);

        assertEquals("System day", result);
    }

    @Test
    void dayTypeReturnsRecoveryDayForOnePercent() {
        String result = HabitService.dayType(100, 1);

        assertEquals("Recovery day", result);
    }

    @Test
    void dayTypeReturnsZeroDayForZeroPercent() {
        String result = HabitService.dayType(10, 0);

        assertEquals("Zero day", result);
    }
}