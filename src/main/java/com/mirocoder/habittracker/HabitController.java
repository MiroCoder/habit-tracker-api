package com.mirocoder.habittracker;

import com.mirocoder.habittracker.dto.DailyStatsUpdateRequest;
import com.mirocoder.habittracker.dto.StatsSummaryResponse;
import com.mirocoder.habittracker.model.DailyStats;
import com.mirocoder.habittracker.model.Habit;
import com.mirocoder.habittracker.service.HabitService;
import org.springframework.web.bind.annotation.*;
import com.mirocoder.habittracker.model.HabitStats;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.mirocoder.habittracker.dto.HabitRequest;
import com.mirocoder.habittracker.dto.HabitStreakResponse;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDate;


import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

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
    public ResponseEntity<Habit> addHabit(@Valid @RequestBody HabitRequest request) {
        Habit createdHabit = habitService.addHabit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHabit);
    }

    @GetMapping("/habits/stats")
    public HabitStats  getStats() {
        return habitService.getStats();
    }

    @GetMapping("/stats/today/message")
    public String getTodayMessage() {
        HabitStats stats = getStats();

        if (stats.getDayType().equals("System day")) {
            return "System day - you invested in future self.";
        }else if (stats.getDayType().equals("Strong day")) {
            return "Strong day - keep going.";
        }else if (stats.getDayType().equals("Recovery day")) {
            return "Recovery day - stay clean.";
        } else if (stats.getDayType().equals("Perfect day")) {
            return "Perfect day - clean investment.";
        } else {
            return "Reset - one action immediately.";
        }

    }

    @GetMapping("/habits/{id}/streak")
    public HabitStreakResponse getHabitStreak(@PathVariable long id) {
        return habitService.getHabitStreak(id);
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

    @PatchMapping("/habits/{id}/uncomplete")
    public ResponseEntity<Habit> markAsNotCompleted(@PathVariable long id) {
        Habit habit = habitService.markAsNotCompleted(id);

        if (habit == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(habit);
    }


    @PutMapping("/habits/{id}")
    public ResponseEntity<Habit> updateHabit(
            @PathVariable long id,
            @Valid @RequestBody HabitRequest request
    ) {
        Habit habit = habitService.updateHabit(id, request);

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

    @GetMapping("/habits/search")
    public ResponseEntity<Habit> seaarchHabit(@RequestParam String name) {
        Habit habit = habitService.findByName(name);

        if (habit == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(habit);
    }

    @GetMapping("/habits/priority/{priority}")
    public List<Habit> getHabitsByPriority(@PathVariable Habit.Priority priority) {
        return habitService.getHabitsByPriority(priority);
    }

    @GetMapping("/habits/not-completed")
    public List<Habit> getNotCompletedHabits() {
        return habitService.getNotCompletedHabits();
    }

    @GetMapping("/stats/history")
    public List<DailyStats> getDailyStatsHistory() {
        return habitService.getDailyStatsHistory();
    }

    @PatchMapping("/stats/history/{date}")
    public DailyStats updateDailyStats(
            @PathVariable LocalDate date,
            @RequestBody DailyStatsUpdateRequest request
    ) {
        return habitService.updateDailyStats(
                date,
                request.getTotal(),
                request.getCompleted()
        );
    }

    @GetMapping("/stats/summary")
    public StatsSummaryResponse getStatsSummary(
        @RequestParam(defaultValue = "7") int days
    ) {
        return habitService.getStatsSummary(days);
    }

    @GetMapping("/habits/next")
    public Habit getNextHabit() {
        return habitService.getNextHabit();
    }

    @GetMapping("system/time")
    public Map<String, Object> getTime() {
        return Map.of(
                "currentMillis", System.currentTimeMillis(),
                "zone", ZoneId.systemDefault().toString()
        );
    }

    @GetMapping("/habits/required")
    public List<Habit> getRequiredToday() {
        return habitService.getRequiredToday();
    }

    @GetMapping("/system/day-status")
    public String getSystemDayStatus() {
        return habitService.getSystemDayStatus();
    }

}