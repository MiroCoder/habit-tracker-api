package com.mirocoder.habittracker;

import com.mirocoder.habittracker.model.Habit;
import com.mirocoder.habittracker.service.HabitService;
import org.springframework.web.bind.annotation.*;
import com.mirocoder.habittracker.model.HabitStats;

import java.util.List;

@RestController
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping("/habits/test")
    public String test() {
        return "Habit API works";
    }

    @GetMapping("/habits")
    public List<Habit> getHabits() {
        return habitService.getAllHabits();
    }

    @PostMapping("/habits")
    public Habit addHabit(@RequestBody Habit habit) {
        return habitService.addHabit(habit);
    }

    @GetMapping("/habits/stats")
    public HabitStats  getStats() {
        return habitService.getStats();
    }

    @GetMapping("/habits/{id}")
    public Habit getHabitById(@PathVariable Long id) {
        return habitService.findById(id);
    }

    @PatchMapping("/habits/{id}/complete")
    public Habit markCompleted(@PathVariable Long id) {
        return habitService.markCompleted(id);
    }
}