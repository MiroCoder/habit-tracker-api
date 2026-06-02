package com.mirocoder.habittracker.repository;

import com.mirocoder.habittracker.model.Habit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HabitRepository {

    private final JdbcTemplate jdbcTemplate;

    public HabitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Habit> findAll() {
        String sql = "SELECT * FROM habits ORDER BY id";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    public Habit findById(long id) {
        String sql = "SELECT * FROM habits WHERE id = ?";
        List<Habit> habits = jdbcTemplate.query(sql, this::mapRow, id);
        return habits.isEmpty() ? null : habits.get(0);
    }

    public Habit findByName(String name) {
        String sql = "SELECT * FROM habits WHERE LOWER(name) = LOWER(?)";
        List<Habit> habits = jdbcTemplate.query(sql, this::mapRow, name);
        return habits.isEmpty() ? null : habits.get(0);
    }

    public List<Habit> findByPriority(Habit.Priority priority) {
        String sql = "SELECT * FROM habits WHERE priority = ? ORDER BY id";
        return jdbcTemplate.query(sql, this::mapRow, priority.name());
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

    public Habit update(Habit habit) {
        String sql = "UPDATE habits SET name = ?, completed = ?, priority = ? WHERE id = ?";

        int updatedRows = jdbcTemplate.update(
                sql,
                habit.getName(),
                habit.isCompleted(),
                habit.getPriority().name(),
                habit.getId()
        );

        return updatedRows == 0 ? null : findById(habit.getId());
    }

    public boolean deleteById(long id) {
        String sql = "DELETE FROM habits WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private Habit mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Habit(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getBoolean("completed"),
                Habit.Priority.valueOf(rs.getString("priority"))
        );
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM habits";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int countCompleted() {
        String sql = "SELECT COUNT(*) FROM habits WHERE completed = true";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public boolean markCompleted(long id) {
        String sql = "UPDATE habits  SET completed = true WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public int resetCompleted() {
        String sql = "UPDATE habits SET completed = false";
        return jdbcTemplate.update(sql);
    }

    public List<Habit> findNotCompleted(){
        String sql = "SELECT * FROM habits WHERE completed = false " +
                    "ORDER BY CASE priority " +
                    "WHEN 'High' THEN 1 " +
                    "WHEN 'Medium' THEN 2 " +
                    "WHEN 'Low' THEN 3 " +
                    "END";
        return jdbcTemplate.query(sql, (rs,rowNum) -> new Habit(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getBoolean("completed"),
                Habit.Priority.valueOf(rs.getString("priority"))
        ));
    }

    public Habit findNextNotCompleted() {
        String sql = """
                SELECT * FROM habits 
                WHERE completed = false 
                ORDER BY 
                    completed ASC,
                    CASE priority
                        WHEN 'High' THEN 1
                        WHEN 'Medium' THEN 2
                        WHEN 'Low' THEN 3
                    END
                """;

        List<Habit> habits = jdbcTemplate.query(sql, (rs, rowNum) -> new Habit(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getBoolean("completed"),
                Habit.Priority.valueOf(rs.getString("priority"))
        ));

        return habits.isEmpty() ? null : habits.get(0);
    }
}
