CREATE TABLE media (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    file_name VARCHAR(255),
    upload_date TIMESTAMP,
    mime_type VARCHAR(100),
    content BIGINT,
    size BIGINT,
    dtype VARCHAR(255) 
    /* default column by Hibernate to 
    store the type of the entity in the 
    database table */
);