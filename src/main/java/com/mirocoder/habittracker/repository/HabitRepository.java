package com.mirocoder.habittracker.repository;

import com.mirocoder.habittracker.model.Habit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class HabitRepository {

    private final JdbcTemplate jdbcTemplate;

    public HabitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Habit> findAll() {
        String sql = "SELECT * FROM habits";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Habit(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBoolean("completed"),
                        Habit.Priority.valueOf(rs.getString("priority"))
                )
        );
    }

    public Habit save(Habit habit) {
        String sql = "INSERT INTO habits (name, completed, priority) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, habit.getName());
            ps.setBoolean(2, habit.isCompleted());
            ps.setString(3, habit.getPriority().name());
            return ps;
        }, keyHolder);

        habit.setId(keyHolder.getKey().longValue());

        return habit;
    }
}