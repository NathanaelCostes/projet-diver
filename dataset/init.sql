-- Création de la base de données
CREATE DATABASE projet_diver_db;

-- Connexion à la base de données
\c projet_diver_db

-- Création de la table Diver
CREATE TABLE diver(
    diverId serial NOT NULL PRIMARY KEY,
    email varchar(30) NOT NULL UNIQUE,
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

