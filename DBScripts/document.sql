CREATE TABLE document (
    id BIGINT PRIMARY KEY REFERENCES media(id),
    number_of_pages INT
);