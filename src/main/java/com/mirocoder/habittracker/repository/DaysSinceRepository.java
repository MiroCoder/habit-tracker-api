package com.mirocoder.habittracker.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mirocoder.habittracker.model.DaysSince;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class DaysSinceRepository {

    private final JdbcTemplate jdbcTemplate;

    public DaysSinceRepository(JdbcTemplate jdbcTemplates) {
        this.jdbcTemplate = jdbcTemplates;
    }

    public List<DaysSince> findAll() {
        String sql = "SELECT * FROM days_since ORDER BY start_date ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
                    LocalDate startDate = rs.getDate("start_date").toLocalDate();
                    long daysCount = ChronoUnit.DAYS.between(startDate, LocalDate.now());

                    return new DaysSince(
                            rs.getLong("id"),
                            rs.getString("name"),
                            startDate,
                            daysCount
                    );
                }
        );
    }

    public DaysSince save(DaysSince item) {
        String sql = "INSERT INTO days_since (name, start_date) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                item.getName(),
                item.getStartDate()
        );
        return item;
    }

}
