CREATE TABLE rating (
    id SERIAL PRIMARY KEY,
    media_id BIGINT,
    title VARCHAR(255),
    description TEXT,
    general_rating REAL,
    tutor_rating REAL,
    content_rating REAL,
    content_structure_rating REAL,
    presentation_rating REAL,
    engagement_rating REAL,
    difficulty_rating REAL
);
CREATE TABLE media (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    file_name VARCHAR(255),
    extension VARCHAR(4),
    upload_date TIMESTAMP,
    mime_type VARCHAR(100),
    content BIGINT,
    size BIGINT,
    dtype VARCHAR(255) 
    /* default column by Hibernate to 
    store the type of the entity in the 
    database table */
);
CREATE TABLE video (
    id BIGINT PRIMARY KEY REFERENCES media(id),
    width INT,
    height INT,
    resolution_quality INT,
    duration BIGINT,
    aspect_ratio BIGINT,
    fps DOUBLE PRECISION
);
CREATE TABLE audio (
    id BIGINT PRIMARY KEY REFERENCES media(id),
    duration INT,
    sample_rate REAL
);
CREATE TABLE document (
    id BIGINT PRIMARY KEY REFERENCES media(id),
    number_of_pages INT
);
CREATE TABLE image (
    id BIGINT PRIMARY KEY REFERENCES media(id),
    width INT,
    height INT,
    resolution_quality INT
);