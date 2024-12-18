package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static String URL = "jdbc:mysql://localhost:3306/digglets"; // Aqui hay que pooner el nombre de la base de datos
    private static String USER = "root";
    private static String PASS = "root";
    private static Connection conn = null;

    public static Connection con() {
        return conn;
    }

    public static boolean crearConexion() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            DBCreation.crearTablas();
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
            System.out.println("Error al cerrar la conexion: " + e.getClass());
        }
    }

    public static boolean status() {
        boolean status = false;
        try {
            System.out
                    .println("Conexión exitosa a la base de datos: "
                            + DBConnection.con().getMetaData().getDatabaseProductName());
            status = true;
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getClass());
            System.out.println("");

        } catch (Exception e) {
            System.out.println("Error desconocido: " + e.getClass());
            System.out.println("");
        }
        return status;
    }

}
