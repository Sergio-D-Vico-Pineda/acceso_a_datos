package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String URL = "jdbc:mysql://localhost:3306/digglets";
    private static String USER = "root";
    private static String PASS = "root";
    private static Connection conn = null;

    public static Connection con() {
        return conn;
    }

    public static boolean crearConexion() {
        conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Posiblemente la base de datos no se haya iniciado. Error:");
            System.out.println("--------------------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("--------------------------------------------------");
        }
        return conn != null;
    }

    public static void cerrarConexion() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }

}
