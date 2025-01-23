-- Active: 1733567256886@@127.0.0.1@3306@repasillo

CREATE TABLE productos (
    id INT PRIMARY KEY,
    nombre VARCHAR(100),
    precio DECIMAL(10, 2),
    stock INT
);

INSERT INTO
    productos (id, nombre, precio, stock)
VALUES (1, 'Producto A', 10.00, 100),
    (4, 'Producto B', 20.00, 50),
    (3, 'Producto C', 15.50, 75);

BEGIN TRANSACTION;

SELECT precio, stock FROM productos WHERE id = 1;

COMMIT;

BEGIN TRANSACTION;

UPDATE productos SET stock = stock - 10 WHERE id = 1;

COMMIT;