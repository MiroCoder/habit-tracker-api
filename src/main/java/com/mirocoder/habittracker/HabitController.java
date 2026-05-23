package com.mirocoder.habittracker;

import com.mirocoder.habittracker.model.Habit;
import com.mirocoder.habittracker.service.HabitService;
import org.springframework.web.bind.annotation.*;
import com.mirocoder.habittracker.model.HabitStats;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Habit> addHabit(@RequestBody Habit habit) {
        Habit createdHabit = habitService.addHabit(habit);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdHabit);

    }

    @GetMapping("/habits/stats")
    public HabitStats  getStats() {
        return habitService.getStats();
    }

    @GetMapping("/habits/{id}")
    public ResponseEntity<Habit> getHabitById(@PathVariable long id) {
        Habit habit = habitService.findById(id);

        if (habit == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(habit);
    }

    @PatchMapping("/habits/{id}/complete")
    public ResponseEntity<Habit> markCompleted(@PathVariable long id) {
        Habit habit = habitService.markCompleted(id);

        if (habit == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(habit);
    }

    @PutMapping("/habits/{id}")
    public ResponseEntity<Habit> updateHabit(
            @PathVariable long id,
            @RequestBody Habit updatedHabit
    ) {
        Habit habit = habitService.updateHabit(id, updatedHabit);

        if (habit == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(habit);
    }

    @DeleteMapping("/habits/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable long id) {
        boolean deleted = habitService.deleteHabit(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }


}