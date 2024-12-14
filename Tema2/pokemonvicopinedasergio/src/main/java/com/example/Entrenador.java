package com.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Entrenador {
    static Scanner input = new Scanner(System.in);

    public static String escribir(String texto) {
        String entrada;

        do {
            System.out.printf("Ingrese el %s del entrenador: ", texto);
            entrada = input.nextLine();
            if (entrada.isBlank()) {
                System.out.printf("El %s del entrenador no puede estar vacío.", texto);
                System.out.println();
                continue;
            }
            break;
        } while (true);

        return entrada;
    }

    public static int escribirInt(String texto) {
        int nivel;

        do {
            try {
                System.out.printf("Ingrese el %s del entrenador: ", texto);
                nivel = input.nextInt();
                input.nextLine();
                if (nivel <= 0) {
                    System.out.printf("El %s del entrenador tiene que ser mayor que 0.", texto);
                    System.out.println();
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.printf("El %s del entrenador no es válido.", texto);
                System.out.println();
                input.nextLine();
            }
        } while (true);

        return nivel;
    }

    public static boolean existeEntrenador(int id) {

        String existePokemonSQL = "SELECT * FROM entrenadores WHERE id = ?";
        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(existePokemonSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                System.out.println("El entrenador con id '" + id + "' no existe.");
            }
        } catch (SQLException e) {
            System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
        }

        return false;
    }

    public static void info(int id) {
        /*
         * if (!existeEntrenador(id))
         * return;
         */

        String infoSQL = "SELECT * FROM entrenadores WHERE id = ?";
        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(infoSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idEnt = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String ciudadOrigen = rs.getString("ciudad_origen");

                System.out.println("ID: " + idEnt + " | Nombre: " + nombre + " | Ciudad de origen: " + ciudadOrigen);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer entrenador: " + e.getMessage());
        }
    }

    public static String getNombre(int id) {
        String infoSQL = "SELECT * FROM entrenadores WHERE id = ?";
        String nombre = "";
        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(infoSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer entrenador: " + e.getMessage());
        }
        return nombre;
    }

    public static void agregarEntrenador(String nombre, String ciudad) {
        String agregarEntrenadorSQL = "INSERT INTO entrenadores (nombre, ciudad_origen) VALUES (?, ?)";
        System.out.println("");

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(agregarEntrenadorSQL);
            pstmt.setString(1, nombre);
            pstmt.setString(2, ciudad);
            pstmt.executeUpdate();

            System.out.println("Entrenador '" + nombre + "' agregado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar entrenador: " + e.getMessage());
        }
    }

    public static void leerEntrenadores() {

        System.out.println(
                "Id de un entrenador para ver sus detalles, cualquier otra cosa para ver todos los entrenadores.");
        String strIdEnt = escribir("id");
        int idEnt;
        System.out.println("");

        try {
            idEnt = Integer.parseInt(strIdEnt);
        } catch (NumberFormatException e) {
            idEnt = -1;
        }

        if (idEnt > 0) {
            info(idEnt);
            return;
        }
        System.out.println("\033[1m" + "Mostrando todos los entrenadores:" + "\033[0m");
        System.out.println("");
        System.out.println("ID | Nombre | Ciudad de origen");
        System.out.println("");

        String leerEntrenadoresSQL = "SELECT * FROM entrenadores";

        try {
            Statement stmt = DBConnection.con().createStatement();
            ResultSet rs = stmt.executeQuery(leerEntrenadoresSQL);

            boolean hasEntrenadores = false;

            while (rs.next()) {
                hasEntrenadores = true;
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String ciudadOrigen = rs.getString("ciudad_origen");

                System.out.println(id + " | " + nombre + " | " + ciudadOrigen);
            }
            if (!hasEntrenadores) {
                System.out.println("No hay entrenadores en la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer entrenadores: " + e.getMessage());
        }
    }

    public static void actualizarEntrenador(int id, String nombre, String ciudad) {
        String actualizarEntrenadorSQL = "UPDATE entrenadores SET nombre = ?, ciudad_origen = ? WHERE id = ?";

        System.out.println("");

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(actualizarEntrenadorSQL);
            pstmt.setString(1, nombre);
            pstmt.setString(2, ciudad);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();

            System.out.println("Entrenador '" + nombre + "' actualizado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar entrenador: " + e.getMessage());
        }
    }

    public static void eliminarEntrenador(int id) {
        info(id);
        System.out.println("");

        System.out.print("¿Está seguro de eliminar este entrenador? (s/n): ");
        String confirmar = input.nextLine();

        System.out.println("");
        if (confirmar.equalsIgnoreCase("s")) {
            String eliminarEntrenadorSQL = "DELETE FROM entrenadores WHERE id = ?";

            try {
                PreparedStatement pstmt = DBConnection.con().prepareStatement(eliminarEntrenadorSQL);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                System.out.println("Entrenador eliminado con exito.");
            } catch (SQLException e) {
                System.out.println("Error al eliminar entrenador: " + e.getMessage());
            }
        } else {
            System.out.println("Cancelado.");
        }
    }

    public static void asignarEntrenadorPokemon(int idEnt, int idPok) {
        String asignarPokemonSQL = "INSERT INTO entrenador_pokemon (entrenador_id, pokemon_id) VALUES (?, ?)";
        // String asignarPokemonSQL = "INSERT INTO entrenador_pokemon (entrenador_id,
        // pokemon_id, fecha) VALUES (?, ?, ?)";
        System.out.println("");

        String nombreEntrenador = getNombre(idEnt);
        String nombrePokemon = Pokemon.getNombre(idPok);

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(asignarPokemonSQL);
            pstmt.setInt(1, idEnt);
            pstmt.setInt(2, idPok);
            pstmt.executeUpdate();

            System.out.println(
                    "Pokémon '" + nombrePokemon + "' ASIGNADO al entrenador '" + nombreEntrenador + "' con exito.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println(
                    "El entrenador '" + nombreEntrenador + "' YA tiene ASIGNADO el pokémon '" + nombrePokemon + "'.");
        } catch (SQLException e) {
            System.out.println("Error desconocido al asignar pokémon: " + e.getMessage());
        }
    }

    public static void desasignarEntrenadorPokemon(int idEnt, int idPok) {
        String eliminarPokemonSQL = "DELETE FROM entrenador_pokemon WHERE entrenador_id = ? AND pokemon_id = ?";
        System.out.println("");

        String nombreEntrenador = getNombre(idEnt);
        String nombrePokemon = Pokemon.getNombre(idPok);

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(eliminarPokemonSQL);
            pstmt.setInt(1, idEnt);
            pstmt.setInt(2, idPok);

            if (pstmt.executeUpdate() == 0) {
                System.out.println(
                        "El entrenador '" + nombreEntrenador + "' NO tiene ASIGNADO el pokémon '" + nombrePokemon
                                + "'.");
                System.out.println("No se ha realizado ninguna modificación.");
            } else {
                System.out.println(
                        "Pokémon '" + nombrePokemon + "' DESASIGNADO del entrenador '" + nombreEntrenador
                                + "' con exito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar pokémon: " + e.getMessage());
        }
    }
}
