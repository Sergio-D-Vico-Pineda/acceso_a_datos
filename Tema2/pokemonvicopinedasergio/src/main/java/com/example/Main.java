package com.example;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static String URL = "jdbc:mysql://localhost:3306/digglets";
    static String USER = "root";
    static String PASS = "root";
    static Connection conn = null;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        crearConexion();

        do {
            System.out.println();
            System.out.println("Ejercicio Durisimo para Aprobar - Acceso a datos - Sergio David Vico Pineda");
            System.out.println();
            try {
                System.out
                        .println("Conexión exitosa a la base de datos: " + conn.getMetaData().getDatabaseProductName());
            } catch (SQLException e) {
                System.out.println("Posiblemente la base de datos no se haya iniciado.");
                // System.out.println("Error al conectar: " + e.getMessage());
                opcion = 0;
                break;
            }
            System.out.println();
            System.out.println("-------- Menú --------");
            System.out.println();
            System.out.println("Pokemons:");
            System.out.println("1. Agregar pokemon.");
            System.out.println("2. Listar pokemones.");
            System.out.println("3. Actualizar pokemon.");
            System.out.println("4. Eliminar pokemon.");
            System.out.println();
            System.out.println("Entrenadores:");
            System.out.println("5. Agregar entrenador.");
            System.out.println("6. Listar entrenadores.");
            System.out.println("7. Actualizar entrenador.");
            System.out.println("8. Eliminar entrenador.");
            System.out.println();
            System.out.println("Opciones:");
            System.out.println("9. Asignar pokemon a un entrenador.*");
            System.out.println("10. Desasignar pokemon a un entrenador.*");
            System.out.println("11. Registrar una nueva batalla entre dos entrenadores.*");
            System.out.println("12. Ver el historial de batallas.*");
            System.out.println();
            System.out.println("Opciones avanzadas:");
            System.out.println("13. Obtener todos los Pokémon de un entrenador específico.*");
            System.out.println("14. Obtener el historial de batallas de un entrenador.*");
            System.out.println("15. Obtener estadísticas de victorias y derrotas de los entrenadores.*");
            System.out.println();
            System.out.println("0. Salir");
            System.out.println();
            System.out.print("Ingrese una opcion: ");

            try {
                opcion = input.nextInt();
            } catch (Exception e) {
                opcion = -1;
                input.next();
            }

            System.out.println();
            input.nextLine();

            switch (opcion) {
                case 0 -> salirPrograma();
                case 1 -> agregarPokemon();
                case 2 -> leerPokemones();
                case 3 -> actualizarPokemon();
                case 4 -> eliminarPokemon();

                case 5 -> agregarEntrenador();
                case 6 -> listaEntrenadores();
                case 7 -> actualizarEntrenador();
                case 8 -> eliminarEntrenador();

                case 9 -> asignarPokemonAEntrenador();
                case 10 -> eliminarPokemonDeEntrenador();
                case 11 -> registrarBatalla();
                case 12 -> verHistorialBatallas();

                case 13 -> obtenerPokemonesDeEntrenador();
                case 14 -> obtenerHistorialBatallasDeEntrenador();
                case 15 -> obtenerEstadisticas();

                default -> defaultOption();
            }

            if (opcion != 0) {
                System.out.println();
                System.out.print("Presiona ENTER para continuar...");
                input.nextLine();
            }

        } while (opcion != 0);
    }

    private static void defaultOption() {
        System.out.println("Opcion invalida.");
    }

    private static void salirPrograma() {
        System.out.println("Programa finalizado. Bye.");
        cerrarConexion();
    }

    private static void crearConexion() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error al connectar la base de datos: " + e.getMessage());
        }
    }

    private static void cerrarConexion() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }

    private static void agregarPokemon() {
        String nombre, tipo, tipo_sec;
        int nivel;

        do {
            try {
                System.out.print("Ingrese el nombre del pokemon: ");
                nombre = input.nextLine();
                if (nombre.isBlank()) {
                    System.out.println("El nombre del pokemon no puede estar vacio.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nombre del pokemon no es valido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el tipo principal del pokemon: ");
                tipo = input.nextLine();
                if (tipo.isBlank()) {
                    System.out.println("El tipo principal del pokemon no puede estar vacio.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El tipo principal del pokemon no es valido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el tipo secundario del pokemon: ");
                tipo_sec = input.nextLine();
                if (tipo_sec.isBlank()) {
                    System.out.println("El tipo secundario del pokemon no puede estar vacio.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El tipo secundario del pokemon no es valido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nivel del pokemon: ");
                nivel = input.nextInt();
                input.nextLine();
                if (nivel < 1 || nivel > 100) {
                    System.out.println("El nivel del pokemon debe estar entre 1 y 100.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nivel del pokemon no es valido.");
                input.nextLine();
            }
        } while (true);

        String agregarPokemonSQL = "INSERT INTO pokemons (nombre, tipo_principal, tipo_secundario, nivel) VALUES (?, ?, ?, ?)";

        System.out.println("");
        try {
            PreparedStatement pstmt = conn.prepareStatement(agregarPokemonSQL);
            pstmt.setString(1, nombre);
            pstmt.setString(2, tipo);
            pstmt.setString(3, tipo_sec);
            pstmt.setInt(4, nivel);
            pstmt.executeUpdate();

            System.out.println("Pokemon agregado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar pokemon: " + e.getMessage());
        }
    }

    private static void leerPokemones() {
        String leerPokemonesSQL = "SELECT * FROM pokemons";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(leerPokemonesSQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo_principal");
                String tipo_sec = rs.getString("tipo_secundario");
                int nivel = rs.getInt("nivel");

                System.out.println("ID: " + id + ", Nombre: " + nombre +
                        ", Tipo principal: " + tipo + ", Tipo secundario: " + tipo_sec + ", Nivel: " + nivel);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokemones: " + e.getMessage());
        }
    }

    private static void actualizarPokemon() {
        int id;
        String nombre;
        String tipo;
        String tipo_sec;
        int nivel;

        boolean pokemonExiste = false;

        do {
            System.out.print("Ingrese el id del pokemon para actualizar: ");
            id = input.nextInt();
            input.nextLine();

            String existePokemonSQL = "SELECT * FROM pokemons WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existePokemonSQL);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    pokemonExiste = true;
                } else {
                    System.out.println("El pokemon con id " + id + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el pokemon existe: " + e.getMessage());
            }
        } while (!pokemonExiste);

        do {
            try {
                System.out.print("Ingrese el nuevo nombre del pokemon: ");
                nombre = input.nextLine();
                if (nombre.isBlank()) {
                    System.out.println("El nombre del pokemon no puede estar vacio.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nombre del pokemon no es valido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nuevo tipo principal del pokemon: ");
                tipo = input.nextLine();
                if (tipo.isBlank()) {
                    System.out.println("El tipo principal del pokemon no puede estar vacio.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El tipo principal del pokemon no es valido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nuevo tipo secundario del pokemon: ");
                tipo_sec = input.nextLine();
                if (tipo_sec.isBlank()) {
                    System.out.println("El tipo secundario del pokemon no puede estar vacio.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El tipo secundario del pokemon no es valido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nuevo nivel del pokemon: ");
                nivel = input.nextInt();
                input.nextLine();
                if (nivel < 1 || nivel > 100) {
                    System.out.println("El nivel del pokemon debe estar entre 1 y 100.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nivel del pokemon no es valido.");
                input.nextLine();
            }
        } while (true);

        String actualizarPokemonSQL = "UPDATE pokemons SET nombre = ?, tipo_principal = ?, tipo_secundario = ?, nivel = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(actualizarPokemonSQL);
            pstmt.setString(1, nombre);
            pstmt.setString(2, tipo);
            pstmt.setString(3, tipo_sec);
            pstmt.setInt(4, nivel);
            pstmt.setInt(5, id);

            System.out.println("");
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Pokemon actualizado con exito.");
            } else {
                System.out.println("Pokemon no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar pokemon: " + e.getMessage());
        }
    }

    private static void eliminarPokemon() {
        int id = 0;
        boolean pokemonExiste = false;

        do {
            try {
                System.out.print("Ingrese el id del pokemon para eliminar: ");
                id = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del pokemon no es válido. Debe ser un número.");
                pokemonExiste = false;
                input.nextLine();
                continue;
            }

            String existePokemonSQL = "SELECT * FROM pokemons WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existePokemonSQL);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    pokemonExiste = true;
                } else {
                    System.out.println("El pokemon con id " + id + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el pokemon existe: " + e.getMessage());
            }
        } while (!pokemonExiste);

        String leerPokemonSQL = "SELECT * FROM pokemons WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(leerPokemonSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo_principal");
                String tipo_sec = rs.getString("tipo_secundario");
                int nivel = rs.getInt("nivel");

                System.out.println("");
                System.out.println("Pokemon a eliminar:");
                System.out.println("Nombre: " + nombre);
                System.out.println("Tipo principal: " + tipo);
                System.out.println("Tipo secundario: " + tipo_sec);
                System.out.println("Nivel: " + nivel);
                System.out.println("");

                System.out.print("¿Está seguro de eliminar este pokemon? (s/n): ");
                String confirmar = input.nextLine();

                System.out.println("");
                if (confirmar.equalsIgnoreCase("s")) {
                    String eliminarPokemonSQL = "DELETE FROM pokemons WHERE id = ?";

                    try {
                        pstmt = conn.prepareStatement(eliminarPokemonSQL);
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();

                        System.out.println("Pokemon eliminado con exito.");
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar pokemon: " + e.getMessage());
                    }
                } else {
                    System.out.println("Cancelado.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokemon: " + e.getMessage());
        }
    }

    private static void agregarEntrenador() {
        String nombre;
        String ciudad;

        do {
            try {
                System.out.print("Ingrese el nombre del entrenador: ");
                nombre = input.nextLine();
                if (nombre.isBlank()) {
                    System.out.println("El nombre del entrenador no puede estar vacio.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nombre del entrenador no es valido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese la ciudad de origen del entrenador: ");
                ciudad = input.nextLine();
                if (ciudad.isBlank()) {
                    System.out.println("La ciudad de origen del entrenador no puede estar vacia.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("La ciudad de origen del entrenador no es valida.");
                input.nextLine();
            }
        } while (true);

        String agregarEntrenadorSQL = "INSERT INTO entrenadores (nombre, ciudad_origen) VALUES (?, ?)";
        System.out.println("");

        try {
            PreparedStatement pstmt = conn.prepareStatement(agregarEntrenadorSQL);
            pstmt.setString(1, nombre);
            pstmt.setString(2, ciudad);
            pstmt.executeUpdate();

            System.out.println("Entrenador agregado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar entrenador: " + e.getMessage());
        }
    }

    private static void listaEntrenadores() {
        String listaEntrenadoresSQL = "SELECT * FROM entrenadores";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(listaEntrenadoresSQL);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String ciudad_origen = rs.getString("ciudad_origen");

                System.out.println("ID: " + id + ", Nombre: " + nombre +
                        ", Ciudad de origen: " + ciudad_origen);
            }

        } catch (SQLException e) {
            System.out.println("Error al leer pokemones: " + e.getMessage());
        }
    }

    private static void actualizarEntrenador() {
        int id;
        String nombre;
        String ciudad;

        do {
            System.out.print("Ingrese el id del entrenador para actualizar: ");
            id = input.nextInt();
            input.nextLine();

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    break;
                } else {
                    System.out.println("El entrenador con id " + id + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nuevo nombre del entrenador: ");
                nombre = input.nextLine();
                if (nombre.isBlank()) {
                    System.out.println("El nombre del entrenador no puede estar vacio.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nombre del entrenador no es valido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese la nueva ciudad de origen del entrenador: ");
                ciudad = input.nextLine();
                if (ciudad.isBlank()) {
                    System.out.println("La ciudad de origen del entrenador no puede estar vacia.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("La ciudad de origen del entrenador no es valida.");
                input.nextLine();
            }
        } while (true);

        String actualizarEntrenadorSQL = "UPDATE entrenadores SET nombre = ?, ciudad_origen = ? WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(actualizarEntrenadorSQL);
            pstmt.setString(1, nombre);
            pstmt.setString(2, ciudad);
            pstmt.setInt(3, id);

            System.out.println("");
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Entrenador actualizado con exito.");
            } else {
                System.out.println("Entrenador no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar entrenador: " + e.getMessage());
        }
    }

    private static void eliminarEntrenador() {
        int id;

        do {
            System.out.print("Ingrese el id del entrenador para eliminar: ");
            id = input.nextInt();
            input.nextLine();

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    break;
                } else {
                    System.out.println("El entrenador con id " + id + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        String leerEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(leerEntrenadorSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String ciudad = rs.getString("ciudad_origen");

                System.out.println("");
                System.out.println("Entrenador a eliminar:");
                System.out.println("Nombre: " + nombre);
                System.out.println("Ciudad de origen: " + ciudad);
                System.out.println("");

                System.out.print("¿Está seguro de eliminar este entrenador? (s/n): ");
                String confirmar = input.nextLine();

                System.out.println("");
                if (confirmar.equalsIgnoreCase("s")) {
                    String eliminarEntrenadorSQL = "DELETE FROM entrenadores WHERE id = ?";

                    try {
                        pstmt = conn.prepareStatement(eliminarEntrenadorSQL);
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();

                        System.out.println("Entrenador eliminado con exito.");
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar entrenador: " + e.getMessage());
                    }
                } else {
                    System.out.println("Cancelado.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al leer entrenador: " + e.getMessage());
        }

    }

    private static void asignarPokemonAEntrenador() {

    }

    private static void eliminarPokemonDeEntrenador() {

    }

    private static void registrarBatalla() {

    }

    private static void verHistorialBatallas() {

    }

    private static void obtenerPokemonesDeEntrenador() {

    }

    private static void obtenerHistorialBatallasDeEntrenador() {

    }

    private static void obtenerEstadisticas() {

    }
}