CREATE TABLE TASKS (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    content TEXT
);