CREATE TABLE media (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    upload_date TIMESTAMP,
    mime_type VARCHAR(100),
    content BYTEA
);