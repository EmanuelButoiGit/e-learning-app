CREATE TABLE video (
    id BIGINT PRIMARY KEY REFERENCES image(id),
    /* width INT,
    height INT,
    resolution_quality INT, */
    duration BIGINT,
    aspect_ratio BIGINT,
    fps DOUBLE PRECISION
);