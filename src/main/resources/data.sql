DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
    id VARCHAR(200) PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(300),
    status VARCHAR(25)
);
