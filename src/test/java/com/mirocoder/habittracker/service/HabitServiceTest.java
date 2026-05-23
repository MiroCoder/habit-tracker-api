package com.mirocoder.habittracker.service;
import com.mirocoder.habittracker.model.Habit;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HabitServiceTest {

    @Test
    void calculateCompletionCountsCompletedHabits() {
        ArrayList<Habit> habits = new ArrayList<>();
        habits.add(new Habit(1L,"Code", true, Habit.Priority.High));
        habits.add(new Habit(2L,"German", false, Habit.Priority.Medium));
        habits.add(new Habit(3L,"Stretch", true, Habit.Priority.Low));

        int result = HabitService.calculateCompletion(habits);

        assertEquals(2, result);
    }

    @Test
    void findHabitFindsByNameIgnoringCase() {
        ArrayList<Habit> habits = new ArrayList<>();
        habits.add(new Habit(1L,"Code", false, Habit.Priority.High));

        Habit result = HabitService.findHabit(habits, "code");

        assertNotNull(result);
        assertEquals("Code", result.getName());
    }

    @Test
    void findHabitReturnsNullWhenNotFound() {
        ArrayList<Habit> habits = new ArrayList<>();
        habits.add(new Habit(1L,"Code", false, Habit.Priority.High));

        Habit result = HabitService.findHabit(habits, "Sleep");

        assertNull(result);
    }

    @Test
    void dayTypeReturnsPerfectDayForHundredPercent() {
        String result = HabitService.dayType(3, 3);

        assertEquals("Perfect day", result);
    }

    @Test
    void dayPercentReturnsCorrectPercent() {
        double result = HabitService.dayPercent(4, 2);

        assertEquals(50.0, result);
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