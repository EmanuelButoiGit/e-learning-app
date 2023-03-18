CREATE TABLE image (
    id BIGINT PRIMARY KEY REFERENCES media(id),
    width INT,
    height INT,
    resolution_quality INT
);