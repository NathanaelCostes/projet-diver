-- Création de la base de données
CREATE DATABASE projet_diver_db;

-- Connexion à la base de données
\c projet_diver_db

-- Création de la table
CREATE TABLE diver(
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