package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class E2CrearTabla {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/repasillo";
        String usuario = "root";
        String contrasena = "root";

        String crearTablaSQL = "CREATE TABLE IF NOT EXISTS Productos (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "nombre VARCHAR(100)," +
                "precio DECIMAL(10, 2)," +
                "cantidad INT)";

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena);
                Statement stmt = conn.createStatement()) {
            stmt.execute(crearTablaSQL);
            System.out.println("Tabla Productos creada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}
