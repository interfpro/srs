CREATE TABLE IF NOT EXISTS cards (
id              VARCHAR(60)  PRIMARY KEY,
deck_id         VARCHAR NOT NULL,
question        VARCHAR,
answer          VARCHAR,
due_date        VARCHAR,
interv          INTEGER,
player_answer   VARCHAR
);