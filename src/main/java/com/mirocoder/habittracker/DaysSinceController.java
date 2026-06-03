package com.mirocoder.habittracker;

import com.mirocoder.habittracker.dto.DaysSinceRequest;
import com.mirocoder.habittracker.model.DaysSince;
import com.mirocoder.habittracker.service.DaysSinceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return daysSinceService.create(request.getName());
    }


}
