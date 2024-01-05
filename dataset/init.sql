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
                      lastName varchar(20) NOT NULL,
                      isAdmin boolean NOT NULL DEFAULT false
);

-- Ajout d'utilisateurs

INSERT INTO diver(email, password, firstName, lastName)
VALUES('zac.jungler@riotgames.com', '1234', 'Zac', 'Jungler', TRUE);

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
    --Coming:
    --receiver int NOT NULL REFERENCES contact(contactId) PRIMARY KEY
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


-- Création de la table review
CREATE TABLE IF NOT EXISTS review(
            reviewId serial NOT NULL PRIMARY KEY,
            diverId int NOT NULL REFERENCES diver(diverId),
            title varchar(30) NOT NULL,
            description varchar(255),
            rating int NOT NULL
);

-- Ajout d'une relation n-n entre review et lesson (fausse relation n-n, puisque une review ne peut être liée qu'à une seule lesson)
CREATE TABLE IF NOT EXISTS lessonReview(
            reviewId int NOT NULL REFERENCES review(reviewId),
            lessonId int NOT NULL REFERENCES lesson(lessonId),
            diverId int NOT NULL REFERENCES diver(diverId),
            PRIMARY KEY(reviewId, lessonId)

);

-- TODO : ajouter une relation n-n entre review et spot
--  (fausse relation n-n, puisque une review ne peut être liée qu'à un seul spot)

-- Ajout de reviews
INSERT INTO review(diverId, title, description, rating)
VALUES(1, 'Review de test', 'Ceci est une review de test', 5);

INSERT INTO review(diverId, title, description, rating)
VALUES(2, 'Review de test 2', 'Ceci est une review de test 2', 4);

-- Ajout des relations n-n entre review et lesson
INSERT INTO lessonReview(reviewId, lessonId, diverId)
VALUES(1, 1, 1);

INSERT INTO lessonReview(reviewId, lessonId, diverId)
VALUES(2, 1, 2);


CREATE TABLE IF NOT EXISTS certification (
                                                certificationId SERIAL PRIMARY KEY,
                                                name VARCHAR(255) NOT NULL,
                                                pending BOOLEAN NOT NULL DEFAULT true,
                                                file BYTEA NOT NULL,
                                                fileName VARCHAR(255) NOT NULL,
                                                levelObtainedLevel INTEGER NOT NULL,
                                                levelObtainedType VARCHAR(255) NOT NULL,
                                                diverId INTEGER NOT NULL,
                                                FOREIGN KEY (diverId) REFERENCES diver(diverId)
);

