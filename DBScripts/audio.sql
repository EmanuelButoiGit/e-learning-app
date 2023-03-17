CREATE TABLE audio (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    file_name VARCHAR(255),
    upload_date TIMESTAMP,
    mime_type VARCHAR(100),
    content BIGINT,
    duration INT,
    sample_rate REAL
);