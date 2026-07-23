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
        int index = (dayOfYear - 1) % phrases.size();

        return phrases.get(index);
    }

    public void save(DailyPhrase dailyPhrase) {
        String sql = "INSERT INTO daily_phrases (phrase, author) VALUES (?,?)";
        jdbcTemplate.update(sql, dailyPhrase.getPhrase(), dailyPhrase.getAuthor());
    }

    public boolean deleteById(long id) {
        String sql = "DELETE FROM daily_phrases WHERE id = ?";
        int deleteRows = jdbcTemplate.update(sql, id);
        return deleteRows > 0;
    }

    public DailyPhrase findById(long id) {
        String sql = "SELECT * FROM daily_phrases WHERE id = ?";
        List<DailyPhrase> phrases = jdbcTemplate.query(sql,
                (rs, rowNum) -> new DailyPhrase(
                        rs.getLong("id"),
                        rs.getString("phrase"),
                        rs.getString("author")
                ),
                id
        );

        if (phrases.isEmpty()) {
            return null;
        }

        return phrases.get(0);
    }

    public boolean update(DailyPhrase dailyPhrase) {
        String sql = " UPDATE daily_phrases SET phrase =?, author = ? WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, dailyPhrase.getPhrase(), dailyPhrase.getAuthor(), dailyPhrase.getId());

        return updatedRows > 0;
    }

    public int countDailyPhrases() {
        String sql = "SELECT COUNT (*) FROM daily_phrases";
        return jdbcTemplate.queryForObject(sql, Integer.class);

    }
}