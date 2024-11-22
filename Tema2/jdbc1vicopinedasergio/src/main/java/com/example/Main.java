package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectDB();

        if (connection != null) {
            InsertDataDC(connection);
            InsertDataMovies(connection);
            ShowDisCom(connection);
            ShowMovies(connection);
            UpdateData(connection);
            DeleteData(connection);
        }
    }

    public static void InsertDataDC(Connection con) {
        String insertarProductoSQL = "INSERT INTO distribution_companies (id, company_name) VALUES (?, ?), (?, ?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(insertarProductoSQL);
            pstmt.setInt(1, 11);
            pstmt.setString(2, "Lionsgate");
            pstmt.setInt(3, 12);
            pstmt.setString(4, "20th Century Fox");
            pstmt.executeUpdate();

            System.out.println("Compañia de distribuciones insertadas con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar compañias de distribuciones: " + e.getMessage());
        }
    }

    public static void InsertDataMovies(Connection con) {
        String sql = "INSERT INTO movies(id, movie_title, imdb_rating, year_released, budget, box_office, distribution_company_id, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?), (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, 11);
            pstmt.setString(2, "Inception");
            pstmt.setDouble(3, 8.8);
            pstmt.setInt(4, 2010);
            pstmt.setDouble(5, 160.00);
            pstmt.setDouble(6, 829.90);
            pstmt.setInt(7, 12);
            pstmt.setString(8, "English");
            pstmt.setInt(9, 12);
            pstmt.setString(10, "Interstellar");
            pstmt.setDouble(11, 8.6);
            pstmt.setInt(12, 2014);
            pstmt.setDouble(13, 165.00);
            pstmt.setDouble(14, 677.50);
            pstmt.setInt(15, 12);
            pstmt.setString(16, "English");
            pstmt.executeUpdate();

            System.out.println("Movies insertadas con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }

    public static void ShowDisCom(Connection con) {
        try {
            String sql = "SELECT * FROM distribution_companies";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();

            System.out.println("Distribution Companies:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("company_name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }

    public static void ShowMovies(Connection con) {
        try {
            String sql = "SELECT * FROM movies";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();

            System.out.println("Movies:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("movie_title");
                double rating = rs.getDouble("imdb_rating");
                int year = rs.getInt("year_released");
                double budget = rs.getDouble("budget");
                double boxOffice = rs.getDouble("box_office");
                int disCompany = rs.getInt("distribution_company_id");
                String language = rs.getString("language");
                System.out.println("ID: " + id + ", Name: " + name + ", Rating: " + rating + ", Year: " + year
                        + ", Budget: " + budget + ", Box Office: " + boxOffice + ", Dis Company: " + disCompany
                        + ", Language: " + language);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }

    public static void UpdateData(Connection con) {
        String sql = "UPDATE distribution_companies SET company_name = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "20th Century Studios");
            pstmt.setInt(2, 12);
            pstmt.executeUpdate();

            System.out.println("Compañia de distribución actualizadas con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    public static void DeleteData(Connection con) {
        String sql = "DELETE FROM movies WHERE movie_title like ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "Interstellar");
            pstmt.executeUpdate();

            System.out.println("Movie eliminada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar movie: " + e.getMessage());
        }
    }

    public static Connection ConnectDB() {
        String url = "jdbc:mysql://localhost:3306/repasillo";
        String usuario = "root";
        String contrasena = "root";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexión exitosa a la base de datos." + conn.getMetaData().getDatabaseProductName());
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }

        return conn;
    }
}