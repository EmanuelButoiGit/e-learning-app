CREATE TABLE audio (
    id BIGINT PRIMARY KEY REFERENCES media(id),
    duration INT,
    sample_rate REAL
);