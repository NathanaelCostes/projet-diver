-- Création de la base de données
CREATE DATABASE projet_diver_db;

-- Connexion à la base de données
\c projet_diver_db

-- Création de la table
CREATE TABLE IF NOT EXISTS diver(
                      diverId serial NOT NULL PRIMARY KEY,
                      email varchar(30) NOT NULL,
                      password varchar(30) NOT NULL,
                      firstName varchar(80) NOT NULL,
                      lastName varchar(20) NOT NULL
);

-- Ajout d'utilisateurs

INSERT INTO diver(email, password, firstName, lastName)
VALUES('zac.jungler@riotgames.com', '1234', 'Zac', 'Jungler');

INSERT INTO diver(email, password, firstName, lastName)
VALUES('lee_sin.jubgler@riotgames.com', '1234', 'Lee Sin', 'Jungler');

INSERT INTO diver(email, password, firstName, lastName)
VALUES('yasuo.midlaner@riotgames.com', '1234', 'Yasuo', 'Midlaner');



-- Create the lesson table
CREATE TABLE IF NOT EXISTS lesson (
                                      lessonId SERIAL PRIMARY KEY,
                                      name VARCHAR(30) NOT NULL,
                                      description VARCHAR(255) NOT NULL,
                                      startDate DATE NOT NULL,
                                      endDate DATE NOT NULL,
                                      type VARCHAR(30) NOT NULL
);

INSERT INTO lesson(name, description, startDate, endDate, type)
VALUES ('Diving in the sea', 'Diving in the sea with a professional diver', '2020-01-01', '2020-01-02', 'THEORIC');

INSERT INTO lesson(name, description, startDate, endDate, type)
VALUES ('Diving in the mountain', 'Diving in the mountain with a professional diver', '2021-01-01', '2021-01-02', 'PRACTICAL');



CREATE TABLE IF NOT EXISTS diverGivesLesson (
                                                  lessonId INTEGER NOT NULL,
                                                  diverId INTEGER NOT NULL,
                                                  PRIMARY KEY (lessonId, diverId),
                                                  FOREIGN KEY (lessonId) REFERENCES lesson(lessonId),
                                                  FOREIGN KEY (diverId) REFERENCES diver(diverId)
);

INSERT INTO diverGivesLesson(lessonId, diverId)
VALUES (1, 1);

CREATE TABLE IF NOT EXISTS diverTakesLesson(
                                                 lessonId INTEGER NOT NULL,
                                                 diverId INTEGER NOT NULL,
                                                 PRIMARY KEY (lessonId, diverId),
                                                 FOREIGN KEY (lessonId) REFERENCES lesson(lessonId),
                                                 FOREIGN KEY (diverId) REFERENCES diver(diverId)
);

INSERT INTO diverTakesLesson(lessonId, diverId)
VALUES (1, 2);