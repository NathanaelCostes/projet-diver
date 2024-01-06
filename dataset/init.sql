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
VALUES('lee_sin.jungler@riotgames.com', '1234', 'Lee Sin', 'Jungler');

INSERT INTO diver(email, password, firstName, lastName)
VALUES('yasuo.midlaner@riotgames.com', '1234', 'Yasuo', 'Midlaner');

-- Création de la table Session
CREATE TABLE session(
    sessionId serial NOT NULL PRIMARY KEY,
    title varchar(30) NOT NULL,
    date date,
    comment varchar(255),
    duration float,
    temp int,
    depth int,
    owner int NOT NULL REFERENCES diver(diverId),
    UNIQUE (title, owner)
    -- spotId int NOT NULL REFERENCES spot(spotId)
);

-- Ajout de sessions

INSERT INTO session(title, date, owner)
VALUES('Session de test', '2019-01-01', 1);

INSERT INTO session(title, date, owner)
VALUES('Session de test 2', '2019-01-02', 2);

INSERT INTO session(title, date, owner)
VALUES('Session de test 3', '2019-01-05', 3);

-- Création de la table Invitation
CREATE TABLE invitation(
    sessionId int NOT NULL REFERENCES session(sessionId),
    receiver int NOT NULL REFERENCES diver(diverId),
    pending boolean NOT NULL DEFAULT true,
    PRIMARY KEY(sessionId, receiver)
);

-- Ajout d'invitations

INSERT INTO invitation(sessionId, receiver, pending)
VALUES(1, 3, true);

INSERT INTO invitation(sessionId, receiver, pending)
VALUES(2, 3, false);

INSERT INTO invitation(sessionId, receiver, pending)
VALUES(3, 2, true);

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

-- Create the Contact table

CREATE TABLE IF NOT EXISTS contact (
    receiver INTEGER NOT NULL REFERENCES diver(diverId),
    sender INTEGER NOT NULL REFERENCES diver(diverId),
    pending BOOLEAN NOT NULL DEFAULT true,
    PRIMARY KEY (receiver, sender)
);

-- Ajout de contacts

INSERT INTO contact(receiver, sender)
VALUES(1, 2);

INSERT INTO contact(receiver, sender, pending)
VALUES(2, 3, false);

INSERT INTO contact(receiver, sender, pending)
VALUES(3, 1, false);