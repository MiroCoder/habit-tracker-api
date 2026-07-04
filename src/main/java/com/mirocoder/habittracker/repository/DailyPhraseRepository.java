package com.mirocoder.habittracker.repository;

import com.mirocoder.habittracker.model.DailyPhrase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DailyPhraseRepository {
    private final JdbcTemplate jdbcTemplate;

    public DailyPhraseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DailyPhrase> findAll() {
        String sql = "SELECT * FROM daily_phrases ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new DailyPhrase(
                rs.getLong("id"),
                rs.getString("phrase"),
                rs.getString("author")
        ));
    }

    public DailyPhrase findPhraseForToday() {
        List<DailyPhrase> phrases = findAll();

        if (phrases.isEmpty()) {
            return null;
        }

        int dayOfYear = LocalDate.now().getDayOfYear();
        int index = (dayOfYear -1) % phrases.size();

        return phrases.get(index);
    }
}
