package com.mirocoder.habittracker.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class AppSettingsRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppSettingsRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public LocalDateTime getLastResetAt() {
        String sql = "SELECT last_reset_at FROM app_settings WHERE id = 1";
        return jdbcTemplate.queryForObject(sql, LocalDateTime.class);
    }

    public void updateLastResetAt(LocalDateTime time) {
        String sql = "UPDATE app_settings SET last_reset_at = ? WHERE id = 1";
        jdbcTemplate.update(sql, time);
    }
}

