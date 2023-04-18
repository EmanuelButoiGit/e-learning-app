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