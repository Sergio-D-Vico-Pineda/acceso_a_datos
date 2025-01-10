package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionC implements Runnable {

    @Override
    public void run() {
        System.out.println("Transaction C Inicio");
        Connection conn = Database.con();

        try {
            conn.setAutoCommit(false);
            String query = "SELECT * FROM productos WHERE id = ? FOR UPDATE";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("nombre") + " " + rs.getDouble("precio") + " "
                        + rs.getInt("cantidad"));
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al ejecutar la transaccion: " + ex.getMessage());
            }
            System.out.println("Error al ejecutar la transaccion: " + e.getMessage());
        }

        System.out.println("Transaction C Fin");
    }
}
