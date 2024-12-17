package com.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Pokemon {
    static Scanner input = new Scanner(System.in);

    // Solicita al usuario que introduzca un valor de tipo cadena y asegura que no
    // esté en blanco, a menos que un flag opcional lo permita.
    public static String escribir(String texto, boolean... permitirVacio) {

        String entrada;

        do {
            System.out.printf("Ingrese el %s del pokémon: ", texto);
            entrada = input.nextLine();
            if (entrada.isBlank() && !(permitirVacio.length > 0)) {
                System.out.printf("El %s del pokémon no puede estar vacío.", texto);
                System.out.println();
                continue;
            }
            break;
        } while (true);

        return entrada;
    }

    // Solicita al usuario que introduzca un valor de tipo entero
    // El flag opcional es para que no imprima el texto
    public static int escribirInt(String texto, boolean... imprimirTexto) {

        int nivel;

        do {
            try {
                if (!(imprimirTexto.length > 0))
                System.out.printf("Ingrese el %s del pokémon: ", texto);
                nivel = input.nextInt();
                input.nextLine();
                if (nivel <= 0) {
                    System.out.printf("El %s del pokémon tiene que ser mayor que 0.", texto);
                    System.out.println();
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.printf("El %s del pokémon no es válido.", texto);
                System.out.println();
                input.nextLine();
            }
        } while (true);

        return nivel;
    }

    // Comprueba si existe un pokemon con el id introducido, devuelve true o false
    public static boolean existePokemon(int id) {

        String existePokemonSQL = "SELECT * FROM pokemons WHERE id = ?";

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(existePokemonSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                System.out.println("El pokémon con id '" + id + "' no existe.");
            }
        } catch (SQLException e) {
            System.out.println("Error al comprobar si el pokémon existe: " + e.getClass());
        }

        return false;
    }

    public static void info(int id) {

        String infoSQL = "SELECT * FROM pokemons WHERE id = ?";

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(infoSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idPok = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo_principal");
                String tipoSec = rs.getString("tipo_secundario");
                int nivel = rs.getInt("nivel");

                System.out.println("ID: " + idPok + " | Nombre: " + nombre +
                        " | Tipo principal: " + tipo + " | Tipo secundario: " + tipoSec + " | Nivel: " + nivel);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokémon: " + e.getClass());
        }
    }

    // Devuelve el nombre de un pokemon
    public static String getNombre(int id) {

        String infoSQL = "SELECT * FROM pokemons WHERE id = ?";
        String nombre = "";

        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(infoSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokémon: " + e.getClass());
        }
        return nombre;
    }

    public static void agregarPokemon(String nombre, String tipo, String tipoSec, int nivel) {
        String agregarPokemonSQL = "INSERT INTO pokemons (nombre, tipo_principal, tipo_secundario, nivel) VALUES (?, ?, ?, ?)";

        System.out.println("");
        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(agregarPokemonSQL);

            pstmt.setString(1, nombre);
            pstmt.setString(2, tipo);
            pstmt.setString(3, tipoSec);
            pstmt.setInt(4, nivel);

            pstmt.executeUpdate();

            System.out.println("Pokémon '" + nombre + "' agregado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar pokémon: " + e.getClass());
        }
    }

    // Muestra por pantalla la información de un pokemon o una lista de todos
    public static void leerPokemons(String strIdPok) {

        int idPok;

        try {
            idPok = Integer.parseInt(strIdPok);
        } catch (NumberFormatException e) {
            idPok = -1;
        }

        if (idPok > 0) {
            info(idPok);
            return;
        }
        System.out.println("\033[1m" + "Mostrando todos los pokémons:" + "\033[0m");
        System.out.println("");
        System.out.println("ID | Nombre | Tipo principal | Tipo secundario | Nivel");
        System.out.println("");

        String leerPokemonesSQL = "SELECT * FROM pokemons";

        try {
            Statement stmt = DBConnection.con().createStatement();
            ResultSet rs = stmt.executeQuery(leerPokemonesSQL);

            boolean hasPokemons = false;

            while (rs.next()) {
                hasPokemons = true;
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo_principal");
                String tipoSec = rs.getString("tipo_secundario");
                int nivel = rs.getInt("nivel");

                System.out.println(id + " | " + nombre + " | " + tipo + " | " + tipoSec + " | " + nivel);
            }
            if (!hasPokemons) {
                System.out.println("No hay pokémons en la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokémons: " + e.getClass());
        }
    }

    // Actualiza toda la informacion de un pokemon
    public static void actualizarPokemon(int id, String nombre, String tipo, String tipoSec, int nivel) {

        String actualizarPokemonSQL = "UPDATE pokemons SET nombre = ?, tipo_principal = ?, tipo_secundario = ?, nivel = ? WHERE id = ?";

        System.out.println("");
        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(actualizarPokemonSQL);

            pstmt.setString(1, nombre);
            pstmt.setString(2, tipo);
            pstmt.setString(3, tipoSec);
            pstmt.setInt(4, nivel);
            pstmt.setInt(5, id);

            pstmt.executeUpdate();

            System.out.println("Pokémon '" + nombre + "' actualizado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar pokémon: " + e.getClass());
        }
    }

    // Elimina un pokemon, comprobando si existe, y si esta asignado a un entrenador
    public static void eliminarPokemon(int id) {

        info(id);
        System.out.println("");

        System.out.print("¿Está seguro de eliminar este pokémon? (s/n): ");
        String confirmar = input.nextLine();

        System.out.println("");
        if (confirmar.equalsIgnoreCase("s")) {
            String eliminarPokemonSQL = "DELETE FROM pokemons WHERE id = ?";

            try {
                PreparedStatement pstmt = DBConnection.con().prepareStatement(eliminarPokemonSQL);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                System.out.println("Pokémon eliminado con exito.");
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(
                        "ERROR: El pokémon no se puede eliminar debido a que está asignado al menos a un entrenador.");
            } catch (SQLException e) {
                System.out.println("Error al eliminar pokémon: " + e.getClass());
            }
        } else {
            System.out.println("Cancelado.");
        }
    }
}
