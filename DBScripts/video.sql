CREATE TABLE video (
    id BIGINT PRIMARY KEY REFERENCES image(id),
    duration BIGINT,
    aspect_ratio BIGINT,
    fps DOUBLE PRECISION
);