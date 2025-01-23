package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static String URL = "jdbc:mysql://localhost:3306/repasillo";
    private static String USER = "root";
    private static String PASS = "root";
    private static Connection conn = null;

    public static Connection con() {
        return conn;
    }

    public static boolean crearConexion() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Posiblemente la base de datos no se haya iniciado. Error:");
            System.out.println(e.getMessage());
        }
        return conn != null;
    }

    public static void cerrarConexion() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion: " + e.getClass());
        }
    }
}
