package com.example;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Random;

public class Batalla {

    public static void registrarBatalla(int idEnt1, int idEnt2) {
        String nombreEntrenador1, nombreEntrenador2;
        int ganadorId, perdedorId;

        if (idEnt1 == idEnt2) {
            System.out.println("El entrenador no puede enfrentarse a sí mismo.");
            return;
        }

        nombreEntrenador1 = Entrenador.getNombre(idEnt1);
        nombreEntrenador2 = Entrenador.getNombre(idEnt2);

        System.out
                .println("Batalla entre los entrenadores: " + nombreEntrenador1 + " y " + nombreEntrenador2 + ".");
        System.out.println("");
        System.out.println("¡Comienza la batalla!");
        System.out.println("---------------------");
        System.out.println("");

        for (int i = 3; i > 0; i--) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Random rand = new Random();
        int randomNum = rand.nextInt(2);
        if (randomNum == 0) {
            ganadorId = idEnt1;
            perdedorId = idEnt2;
            System.out.println(
                    "El ganador es: " + nombreEntrenador1 + " y el perdedor es: " + nombreEntrenador2 + ".");
        } else {
            ganadorId = idEnt2;
            perdedorId = idEnt1;
            System.out.println(
                    "El ganador es: " + nombreEntrenador2 + " y el perdedor es: " + nombreEntrenador1 + ".");
        }

        String registrarBatallaSQL = "INSERT INTO batallas (ganador_id, perdedor_id, fecha) VALUES (?, ?, ?)";
        System.out.println("");

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(registrarBatallaSQL);
            pstmt.setInt(1, ganadorId);
            pstmt.setInt(2, perdedorId);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.executeUpdate();

            System.out.println("Batalla registrada con exito.");
        } catch (SQLException e) {
            System.out.println("Error al registrar batalla: " + e.getMessage());
        }
    }

    public static void historial(int id) {

        String nombreEntrenador = Entrenador.getNombre(id);

        System.out.println("Las batallas de " + nombreEntrenador + " son: ");
        System.out.println();

        String sql = "SELECT b.id, b.ganador_id, b.perdedor_id, b.fecha FROM batallas b "
                +
                "INNER JOIN entrenadores e ON b.ganador_id = e.id OR b.perdedor_id = e.id WHERE e.id = ?";

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                /* int id = rs.getInt("id"); */
                String ganadorId = rs.getString("ganador_id");
                String perdedorId = rs.getString("perdedor_id");
                String fecha = rs.getString("fecha");

                String nombreGanador = "", nombrePerdedor = "";

                nombreGanador = Entrenador.getNombre(Integer.parseInt(ganadorId));
                nombrePerdedor = Entrenador.getNombre(Integer.parseInt(perdedorId));
                /*
                 * if (rsGanador.next()) {
                 * } else {
                 * System.out.println("El entrenador con id " + ganadorId + " no existe.");
                 * }
                 */

                /*
                 * if (rsPerdedor.next()) {
                 * nombrePerdedor = rsPerdedor.getString("nombre");
                 * } else {
                 * System.out.println("El entrenador con id " + perdedorId + " no existe.");
                 * }
                 */

                System.out.println("Fecha: " + fecha +
                        " | Ganador: " + nombreGanador + " (" + ganadorId + ") y Perdedor: " + nombrePerdedor
                        + " (" + perdedorId + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer batallas: " + e.getMessage());
        }
    }

    public static void historial() {
        String leerBatallas = "SELECT * FROM batallas";

        try {
            Statement stmt = DBConnection.con().createStatement();
            ResultSet rs = stmt.executeQuery(leerBatallas);

            while (rs.next()) {
                /* int id = rs.getInt("id"); */
                String fecha = rs.getString("fecha");
                String ganadorId = rs.getString("ganador_id");
                String perdedorId = rs.getString("perdedor_id");

                String nombreGanador = "", nombrePerdedor = "";

                nombreGanador = Entrenador.getNombre(Integer.parseInt(ganadorId));
                nombrePerdedor = Entrenador.getNombre(Integer.parseInt(perdedorId));

                System.out.println("Fecha: " + fecha +
                        ", Ganador: " + nombreGanador + " (" + ganadorId + "), Perdedor: " + nombrePerdedor + " ("
                        + perdedorId + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer las batallas: " + e.getMessage());
        }
    }

}
