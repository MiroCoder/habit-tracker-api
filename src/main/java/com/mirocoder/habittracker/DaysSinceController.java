package com.mirocoder.habittracker;

import com.mirocoder.habittracker.dto.DailyStatsUpdateRequest;
import com.mirocoder.habittracker.dto.DaysSinceRequest;
import com.mirocoder.habittracker.model.DailyStats;
import com.mirocoder.habittracker.model.DaysSince;
import com.mirocoder.habittracker.service.DaysSinceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DaysSinceController {

    private final DaysSinceService daysSinceService;

    public DaysSinceController(DaysSinceService daysSinceService) {
        this.daysSinceService = daysSinceService;
    }

    @GetMapping("/days-since")
    public List<DaysSince> getAll() {
        return daysSinceService.getAll();
    }

    @PostMapping("/days-since")
    public DaysSince create(@RequestBody DaysSinceRequest request) {
        return daysSinceService.create(request.getName(), request.getStartDate());
    }

    @PatchMapping("/days-since/{id}/start-date")
    public void updateStartDate(
            @PathVariable long id,
            @RequestBody DaysSinceRequest request
    ) {
        daysSinceService.updateStartDate(
                id, request.getStartDate());

    }

    @DeleteMapping("/days-since/{id}")
    public void deleteById (
            @PathVariable long id
    ) {
        daysSinceService.deleteById(id);
        
    }


}
