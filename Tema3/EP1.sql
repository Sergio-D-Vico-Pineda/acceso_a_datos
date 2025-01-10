-- Active: 1733567256886@@127.0.0.1@3306@repasillo
CREATE TABLE IF NOT EXISTS `PRODUCTOS` (
    ID INT PRIMARY KEY,
    NOMBRE VARCHAR(50),
    PRECIO DECIMAL(5, 2),
    CANTIDAD INT
);

INSERT INTO
    productos (id, nombre, precio, cantidad)
VALUES (1, 'Producto A', 10.00, 100),
    (4, 'Producto B', 20.00, 50),
    (3, 'Producto C', 15.50, 75);