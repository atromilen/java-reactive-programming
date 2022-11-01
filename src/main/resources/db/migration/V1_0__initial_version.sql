CREATE TABLE IF NOT EXISTS director (
    director_id SERIAL PRIMARY KEY,
    name VARCHAR (255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS movie (
    movie_id SERIAL PRIMARY KEY,
    name VARCHAR (255) UNIQUE NOT NULL,
    year INTEGER NOT NULL,
    director INTEGER REFERENCES director(director_id)
);
