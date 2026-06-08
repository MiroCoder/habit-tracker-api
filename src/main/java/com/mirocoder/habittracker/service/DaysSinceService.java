package com.mirocoder.habittracker.service;

import com.mirocoder.habittracker.model.DaysSince;
import com.mirocoder.habittracker.repository.DaysSinceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DaysSinceService {

    private final DaysSinceRepository daysSinceRepository;

    public DaysSinceService(DaysSinceRepository daysSinceRepository) {
        this.daysSinceRepository = daysSinceRepository;
    }

    public List<DaysSince> getAll() {
        return daysSinceRepository.findAll();
    }

    public DaysSince create(String name, LocalDate startDate) {
        DaysSince item = new DaysSince(
                0,
                name,
                startDate,
                0
        );

        return daysSinceRepository.save(item);
    }

    public void updateStartDate(long id, LocalDate startDate) {
        daysSinceRepository.updateStartDate(id, startDate);
    }

    public void deleteById(long id) {
        daysSinceRepository.deleteById(id);
    }
}