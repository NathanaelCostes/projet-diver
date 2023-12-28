-- If the database does not exist, create it
DROP DATABASE IF EXISTS diver_db;
CREATE DATABASE diver_db;

-- Connect to the diver_db database
\c diver_db;

-- Create the diver table
CREATE TABLE IF NOT EXISTS diver (
    diver_id SERIAL PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(80) NOT NULL,
    password VARCHAR(100) NOT NULL,
    admin BOOLEAN NOT NULL
    );

-- Create the lesson table
CREATE TABLE IF NOT EXISTS lesson (
   lesson_id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    type VARCHAR(30) NOT NULL
);


CREATE TABLE IF NOT EXISTS diver_gives_lesson (
            lesson_id INTEGER NOT NULL,
            diver_id INTEGER NOT NULL,
            PRIMARY KEY (lesson_id, diver_id),
            FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id),
            FOREIGN KEY (diver_id) REFERENCES diver(diver_id)
);

CREATE TABLE IF NOT EXISTS diver_takes_lesson(
            lesson_id INTEGER NOT NULL,
            diver_id INTEGER NOT NULL,
            PRIMARY KEY (lesson_id, diver_id),
             FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id),
             FOREIGN KEY (diver_id) REFERENCES diver(diver_id)
);
