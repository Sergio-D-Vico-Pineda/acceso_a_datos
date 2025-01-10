package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionB implements Runnable {

    @Override
    public void run() {
        System.out.println("Transaction B Inicio");
        
        try {
            Connection conn = Database.con();
            String query = "UPDATE productos SET cantidad = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 120);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la transaccion: " + e.getMessage());
        }
        
        System.out.println("Transaction B Fin");
    }
}
