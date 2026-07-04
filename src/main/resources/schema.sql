CREATE TABLE IF NOT EXISTS habits (
    id BIGSERIAL PRIMARY KEY,
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
SELECT 1, CURRENT_TIMESTAMP
    WHERE NOT EXISTS (
    SELECT 1 FROM app_settings WHERE id = 1
);

CREATE TABLE IF NOT EXISTS daily_stats (
    id BIGSERIAL PRIMARY KEY,
    stat_date DATE NOT NULL UNIQUE,
    total  INT NOT NULL,
    completed INT NOT NULL,
    not_completed INT NOT NULL,
    percent DOUBLE PRECISION NOT NULL,
    day_type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS days_since (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL
);

INSERT INTO daily_phrases (phrase, author)
SELECT 'Some things are in our control and others not.', 'Epictetus'
    WHERE NOT EXISTS (
    SELECT 1 FROM daily_phrases
    WHERE phrase = 'Some things are in our control and others not.'
);

INSERT INTO daily_phrases (phrase, author)
SELECT 'Waste no more time arguing what a good man should be. Be one.', 'Marcus Aurelius'
    WHERE NOT EXISTS (
    SELECT 1 FROM daily_phrases
    WHERE phrase = 'Waste no more time arguing what a good man should be. Be one.'
);

INSERT INTO daily_phrases (phrase, author)
SELECT 'Difficulties strengthen the mind, as labor does the body.', 'Seneca'
    WHERE NOT EXISTS (
    SELECT 1 FROM daily_phrases
    WHERE phrase = 'Difficulties strengthen the mind, as labor does the body.'
);

CREATE TABLE IF NOT EXISTS habit_completions (
                                                 id BIGSERIAL PRIMARY KEY,
                                                 habit_id BIGINT NOT NULL,
                                                 completion_date DATE NOT NULL,
                                                 CONSTRAINT uq_habit_completion_day UNIQUE (habit_id, completion_date)
    );

CREATE TABLE IF NOT EXISTS daily_phrase (
    id BIGSERIAL PRIMARY KEY,
    phrase TEXT NOT NULL,
    author VARCHAR(255)
)

ALTER TABLE habits ADD COLUMN IF NOT EXISTS required_today BOOLEAN DEFAULT FALSE;