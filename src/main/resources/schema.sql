DROP TABLE IF EXISTS recipe;
CREATE TABLE recipe (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    title             VARCHAR(100),
    description       VARCHAR(1000),
    prep_time_minutes INT,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

