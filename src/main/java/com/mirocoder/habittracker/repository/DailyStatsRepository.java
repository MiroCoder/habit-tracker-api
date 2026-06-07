package com.mirocoder.habittracker.repository;

import com.mirocoder.habittracker.model.DailyStats;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;


@Repository
public class DailyStatsRepository {

    private final JdbcTemplate jdbcTemplate;

    public DailyStatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DailyStats> findLastDays(int days) {
        String sql = "SELECT * FROM daily_stats ORDER BY stat_date DESC LIMIT ?";

        return jdbcTemplate.query(sql,(rs,rowNum) -> new DailyStats(
                rs.getLong("id"),
                rs.getDate("stat_date").toLocalDate(),
                rs.getInt("total"),
                rs.getInt("completed"),
                rs.getInt("not_completed"),
                rs.getDouble("percent"),
                rs.getString("day_type")
        ), days);
    }

    public DailyStats save(DailyStats stats){
        String sql = "INSERT INTO daily_stats " +
                "(stat_date, total, completed, not_completed, percent, day_type)" +
                " VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                stats.getStatDate(),
                stats.getTotal(),
                stats.getCompleted(),
                stats.getNotCompleted(),
                stats.getPercent(),
                stats.getDayType()
        );
        return stats;
    }

    public List<DailyStats> findAll() {
        String sql = "SELECT * FROM daily_stats ORDER BY stat_date DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new DailyStats(
                rs.getLong("id"),
                rs.getDate("stat_date").toLocalDate(),
                rs.getInt("total"),
                rs.getInt("completed"),
                rs.getInt("not_completed"),
                rs.getDouble("percent"),
                rs.getString("day_type")
        ));
    }

    public int update(DailyStats stats) {
        String sql = """
            UPDATE daily_stats
            SET total = ?,
                completed = ?,
                not_completed = ?,
                percent = ?,
                day_type = ?
            WHERE stat_date = ?
            """;

        return jdbcTemplate.update(sql,
                stats.getTotal(),
                stats.getCompleted(),
                stats.getNotCompleted(),
                stats.getPercent(),
                stats.getDayType(),
                stats.getStatDate()
        );
    }

    public void deleteByDate(LocalDate date) {
        String sql = "DELETE FROM daily_stats WHERE stat_date = ?";
        jdbcTemplate.update(sql, date);
    }

    public DailyStats saveOrUpdate(DailyStats stats) {
        deleteByDate(stats.getStatDate());
        return save(stats);
    }


}
