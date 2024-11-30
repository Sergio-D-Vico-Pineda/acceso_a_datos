-- Active: 1731610467265@@127.0.0.1@3306@digglets
CREATE TABLE IF NOT EXISTS pokemons (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    tipo_principal VARCHAR(255) NOT NULL,
    tipo_secundario VARCHAR(255) NULL,
    nivel INT NOT NULL
);

CREATE TABLE IF NOT EXISTS entrenadores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    ciudad_origen VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS batallas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    ganador_id INT NOT NULL,
    perdedor_id INT NOT NULL,
    FOREIGN KEY (ganador_id) REFERENCES entrenadores (id),
    FOREIGN KEY (perdedor_id) REFERENCES entrenadores (id)
);

CREATE TABLE IF NOT EXISTS entrenador_pokemon (
    entrenador_id INT,
    pokemon_id INT,
    PRIMARY KEY (entrenador_id, pokemon_id),
    FOREIGN KEY (entrenador_id) REFERENCES entrenadores (id),
    FOREIGN KEY (pokemon_id) REFERENCES pokemons (id)
);