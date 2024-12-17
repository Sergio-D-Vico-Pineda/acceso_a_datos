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

    // Solicita al usuario que introduzca un valor de tipo cadena y asegura que no
    // esté en blanco, a menos que un flag opcional lo permita.
    public static String escribir(String texto, boolean... permitirVacio) {
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

    // Solicita al usuario que introduzca un valor de tipo entero
    // El flag opcional es para que no imprima el texto
    public static int escribirInt(String texto, boolean... imprimirTexto) {
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

    // Comprueba si existe un entrenador con el id introducido, devuelve true o
    // false
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
            System.out.println("Error al comprobar si el entrenador existe: " + e.getClass());
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
            System.out.println("Error al leer entrenador: " + e.getClass());
        }
    }

    // Devuelve el nombre de un entrenador
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
            System.out.println("Error al leer entrenador: " + e.getClass());
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
            System.out.println("Error al agregar entrenador: " + e.getClass());
        }
    }

    // Muestra por pantalla la información de un entrenador o una lista de todos
    public static void leerEntrenadores(String strIdEnt) {

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
            System.out.println("Error al leer entrenadores: " + e.getClass());
        }
    }

    // Actualiza toda la informacion de un entrenador
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
            System.out.println("Error al actualizar entrenador: " + e.getClass());
        }
    }

    // Elimina un entrenador, comprobando si existe, si tiene algun pokemon o si ha
    // participado en alguna batalla
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
                System.out.println("Error al eliminar entrenador: " + e.getClass());
            }
        } else {
            System.out.println("Cancelado.");
        }
    }

    // Asigna un pokemon a un entrenador, comprobando si ambos existen y que no
    // esten ya relacionados
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
            System.out.println("Error desconocido al asignar pokémon: " + e.getClass());
        }
    }

    // Desasigna un pokemon a un entrenador, comprobando si ambos existen y que
    // esten relacionados
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
            System.out.println("Error al eliminar pokémon: " + e.getClass());
        }
    }

    // Muestra por pantalla la lista de pokemons que tiene un entrenador
    public static void leerPokemonsEntrenador(int id) {
        String nombreEntrenador = getNombre(id);

        System.out.println("Los pokémon de " + nombreEntrenador + " son: ");
        System.out.println();

        // sql inner join entre entrenador_pokemon y pokemons
        // para sacar los datos del pokemon
        String sql = "SELECT p.id, p.nombre, p.tipo_principal, p.tipo_secundario, p.nivel FROM pokemons p "
                +
                "INNER JOIN entrenador_pokemon ep ON p.id = ep.pokemon_id WHERE ep.entrenador_id = ?";

        boolean existenPokemones = false;
        try {
            PreparedStatement pstmt = DBConnection.con().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                existenPokemones = true;
                int idPok = rs.getInt("id");
                String nombre = rs.getString("nombre");

                System.out.println("ID: " + idPok + " | Nombre: " + nombre);
            }

            if (!existenPokemones) {
                System.out.println("No tiene pokémons.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokémons: " + e.getClass());
        }

    }

    // Muestra por pantalla las estadísticas de un entrenador
    public static void estadisticas(int id) {
        String nombreEntrenador;

        nombreEntrenador = getNombre(id);

        String sql = "SELECT COUNT(*) AS batallas_ganadas FROM batallas WHERE ganador_id = ?";
        String sql2 = "SELECT COUNT(*) AS batallas_perdidas FROM batallas WHERE perdedor_id = ?";

        try {
            int batallasGanadas = 0;
            int batallasPerdidas = 0;

            PreparedStatement pstmt = DBConnection.con().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            PreparedStatement pstmt2 = DBConnection.con().prepareStatement(sql2);
            pstmt2.setInt(1, id);
            ResultSet rs2 = pstmt2.executeQuery();

            while (rs.next() && rs2.next()) {
                batallasGanadas = rs.getInt("batallas_ganadas");
                batallasPerdidas = rs2.getInt("batallas_perdidas");
            }

            System.out.println("Estadísticas de " + nombreEntrenador + ":");
            System.out.println();
            System.out.println("Batallas ganadas: " + batallasGanadas);
            System.out.println("Batallas perdidas: " + batallasPerdidas);

        } catch (SQLException e) {
            System.out.println("Error al leer estadísticas: " + e.getClass());
        }
    }
}
