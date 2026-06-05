CREATE TABLE IF NOT EXISTS habits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    completed BOOLEAN NOT NULL,
    priority VARCHAR(20) NOT NULL,
    required_today BOOLEAN NOT NULL DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS app_settings (
    id INT PRIMARY KEY,
    last_reset_at TIMESTAMP NOT NULL
);

INSERT INTO app_settings (id, last_reset_at)
SELECT 1, TIMESTAMP '2010-01-01 00:00:00'
    WHERE NOT EXISTS (
    SELECT 1 FROM app_settings WHERE id = 1
);

CREATE TABLE IF NOT EXISTS daily_stats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    stat_date DATE NOT NULL UNIQUE,
    total  INT NOT NULL,
    completed INT NOT NULL,
    not_completed INT NOT NULL,
    percent DOUBLE PRECISION NOT NULL,
    day_type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS days_since (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL
)