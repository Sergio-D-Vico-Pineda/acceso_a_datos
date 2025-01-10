package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionB implements Runnable {

    @Override
    public void run() {
        System.out.println("Transaction B Inicio");
        Connection conn = Database.con();
        try {
            conn.setAutoCommit(false);
            String query = "UPDATE productos SET cantidad = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 120);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la transaccion: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al ejecutar la transaccion: " + ex.getMessage());
            }
        }

        System.out.println("Transaction B Fin");
    }
}
