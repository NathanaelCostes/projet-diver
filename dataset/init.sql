-- Création de la base de données
CREATE DATABASE projet_diver_db;

-- Connexion à la base de données
\c projet_diver_db

-- Création de la table
CREATE TABLE IF NOT EXISTS diver(
                      id serial NOT NULL PRIMARY KEY,
                      email varchar(30) NOT NULL,
                      password varchar(30) NOT NULL,
                      first_name varchar(80) NOT NULL,
                      last_name varchar(20) NOT NULL
);

-- Ajout d'utilisateurs

INSERT INTO diver(email, password, first_name, last_name)
VALUES('zac.jungler@riotgames.com', '1234', 'Zac', 'Jungler');

INSERT INTO diver(email, password, first_name, last_name)
VALUES('lee_sin.jubgler@riotgames.com', '1234', 'Lee Sin', 'Jungler');

INSERT INTO diver(email, password, first_name, last_name)
VALUES('yasuo.midlaner@riotgames.com', '1234', 'Yasuo', 'Midlaner');



-- Create the lesson table
CREATE TABLE IF NOT EXISTS lesson (
                                      lesson_id SERIAL PRIMARY KEY,
                                      name VARCHAR(30) NOT NULL,
                                      description VARCHAR(255) NOT NULL,
                                      start_date DATE NOT NULL,
                                      end_date DATE NOT NULL,
                                      type VARCHAR(30) NOT NULL
);

INSERT INTO lesson(name, description, start_date, end_date, type)
VALUES ('Diving in the sea', 'Diving in the sea with a professional diver', '2020-01-01', '2020-01-02', 'THEORY');

INSERT INTO lesson(name, description, start_date, end_date, type)
VALUES ('Diving in the mountain', 'Diving in the mountain with a professional diver', '2021-01-01', '2021-01-02', 'PRACTICAL');



CREATE TABLE IF NOT EXISTS diver_gives_lesson (
                                                  lesson_id INTEGER NOT NULL,
                                                  diver_id INTEGER NOT NULL,
                                                  PRIMARY KEY (lesson_id, diver_id),
                                                  FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id),
                                                  FOREIGN KEY (diver_id) REFERENCES diver(id)
);

INSERT INTO diver_gives_lesson(lesson_id, diver_id)
VALUES (1, 1);

CREATE TABLE IF NOT EXISTS diver_takes_lesson(
                                                 lesson_id INTEGER NOT NULL,
                                                 diver_id INTEGER NOT NULL,
                                                 PRIMARY KEY (lesson_id, diver_id),
                                                 FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id),
                                                 FOREIGN KEY (diver_id) REFERENCES diver(id)
);

INSERT INTO diver_takes_lesson(lesson_id, diver_id)
VALUES (1, 2);