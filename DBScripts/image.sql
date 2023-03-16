CREATE TABLE image (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    file_name VARCHAR(255),
    upload_date TIMESTAMP,
    mime_type VARCHAR(100),
    content BIGINT,
    size BIGINT,
    width INT,
    height INT,
    resolution_quality INT
);