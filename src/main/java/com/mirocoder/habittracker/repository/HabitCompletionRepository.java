package com.mirocoder.habittracker.repository;

import com.mirocoder.habittracker.model.HabitCompletion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public class HabitCompletionRepository {

    private final JdbcTemplate jdbcTemplate;
    public HabitCompletionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(long habitId, LocalDate date) {
        String sql = """
                INSERT INTO habit_completions (habit_id, completion_date)
                VALUES (?, ?)
                ON CONFLICT (habit_id, completion_date) DO NOTHING
                """;

        jdbcTemplate.update(sql, habitId, date);
    }

    public void deleteByHabitIdAndDate(long habitId, LocalDate date) {
        String sql = "DELETE FROM habit_completions WHERE habit_id = ? AND completion_date = ?";
        jdbcTemplate.update(sql, habitId, date);
    }

    public List<LocalDate> findCompletionDates(long habitId) {
        String sql = """
                SELECT completion_date
                FROM habit_completions
                WHERE habit_id = ?
                ORDER BY completion_date DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getDate("completion_date").toLocalDate(), habitId);
    }
}