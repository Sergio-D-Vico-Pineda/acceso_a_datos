package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TransactionA implements Runnable {

    @Override
    public void run() {
        System.out.println("Transaction A Inicio");
        Connection conn = Database.con();
        Scanner sc = new Scanner(System.in);

        try {
            conn.setAutoCommit(false);
            String query = "UPDATE productos SET precio = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, 1.50);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();

            /* System.out.println("Presiona enter para finalizar la transaccion");
            sc.nextLine(); */

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al ejecutar la transaccion: " + ex.getMessage());
            }
            System.out.println("Error al ejecutar la transaccion: " + e.getMessage());
        }

        sc.close();
        System.out.println("Transaction A Fin");
    }
}
