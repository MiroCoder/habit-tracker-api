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
                rs.getString("author"),
                rs.getBoolean("active")
        ));
    }

    public DailyPhrase findPhraseForToday() {
        List<DailyPhrase> phrases = findActivePhrases();

        if (phrases.isEmpty()) {
            return null;
        }

        int dayOfYear = LocalDate.now().getDayOfYear();
        int index = (dayOfYear - 1) % phrases.size();

        return phrases.get(index);
    }

    public List<DailyPhrase> findActivePhrases() {
        String sql = "SELECT * FROM daily_phrases WHERE active = TRUE ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new DailyPhrase(
                rs.getLong("id"),
                rs.getString("phrase"),
                rs.getString("author"),
                rs.getBoolean("active")
        ));
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
                        rs.getString("author"),
                        rs.getBoolean("active")
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

    public long countDailyPhrases() {
        String sql = "SELECT COUNT (*) FROM daily_phrases";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<DailyPhrase> findByAuthor(String author) {
        String sql = "SELECT * FROM daily_phrases WHERE LOWER(author) = LOWER(?)";
        List <DailyPhrase> phrases = jdbcTemplate.query(sql,
                (rs, rowNum) -> new DailyPhrase(
                        rs.getLong("id"),
                        rs.getString("phrase"),
                        rs.getString("author"),
                        rs.getBoolean("active")
                ), author
        );

        return  phrases;
    }

    public boolean existsByPhraseAndAuthor(String phrase, String author) {
        String sql = "SELECT COUNT (*) FROM daily_phrases " +
                "WHERE LOWER(phrase) = LOWER(?) " +
                "AND LOWER(author) = LOWER(?)";

        long count = jdbcTemplate.queryForObject(sql, Long.class, phrase, author);

        return count > 0;
    }

    public List<String> findAllAuthors() {
        String sql = "SELECT DISTINCT author FROM daily_phrases WHERE author IS NOT NULL AND TRIM(author) <> '' ORDER BY author";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public boolean updateActiveStatus(long id, boolean active){
        String sql = "UPDATE daily_phrases SET active = ? WHERE id = ?";
        return jdbcTemplate.update(sql, active, id) > 0;
    }
}
