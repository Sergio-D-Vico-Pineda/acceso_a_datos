package com.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
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
        if (conn == null) {
            salirPrograma();
            return;
        }

        do {
            System.out.println();
            System.out.println("Ejercicio Durisimo para Aprobar - Acceso a datos - Sergio David Vico Pineda");
            System.out.println();
            try {
                System.out
                        .println("Conexión exitosa a la base de datos: " + conn.getMetaData().getDatabaseProductName());
            } catch (SQLException e) {
                System.out.println("Error al conectar: " + e.getMessage());
                System.out.println("");
                break;
            } catch (Exception e) {
                System.out.println("Error desconocido: " + e.getMessage());
                System.out.println("");
                salirPrograma();
                break;
            }
            System.out.println();
            System.out.println("-------- Menú --------");
            System.out.println();
            System.out.println("Pokémons:");
            System.out.println("1. Agregar pokémon.");
            System.out.println("2. Listar pokémons.");
            System.out.println("3. Actualizar pokémon.");
            System.out.println("4. Eliminar pokémon.");
            System.out.println();
            System.out.println("Entrenadores:");
            System.out.println("5. Agregar entrenador.");
            System.out.println("6. Listar entrenadores.");
            System.out.println("7. Actualizar entrenador.");
            System.out.println("8. Eliminar entrenador.");
            System.out.println();
            System.out.println("Opciones:");
            System.out.println("9. Asignar pokémon a un entrenador.");
            System.out.println("10. Desasignar pokémon a un entrenador.");
            System.out.println("11. Registrar una nueva batalla entre dos entrenadores.");
            System.out.println("12. Ver el historial de batallas.");
            System.out.println();
            System.out.println("Opciones avanzadas:");
            System.out.println("13. Obtener todos los pokémons de un entrenador específico.");
            System.out.println("14. Obtener el historial de batallas de un entrenador.");
            System.out.println("15. Obtener estadísticas de victorias y derrotas de los entrenadores.");
            System.out.println();
            System.out.println("0. Salir");
            System.out.println();
            System.out.print("Ingrese una opción: ");

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
        System.out.println("");
        if (conn != null)
            cerrarConexion();
    }

    private static void crearConexion() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Posiblemente la base de datos no se haya iniciado. Error:");
            System.out.println("--------------------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("--------------------------------------------------");
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
                System.out.print("Ingrese el nombre del pokémon: ");
                nombre = input.nextLine();
                if (nombre.isBlank()) {
                    System.out.println("El nombre del pokémon no puede estar vacío.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nombre del pokémon no es válido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el tipo principal del pokémon: ");
                tipo = input.nextLine();
                if (tipo.isBlank()) {
                    System.out.println("El tipo principal del pokémon no puede estar vacío.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El tipo principal del pokémon no es válido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el tipo secundario del pokémon: ");
                tipo_sec = input.nextLine();
                if (tipo_sec.isBlank()) {
                    System.out.println("El tipo secundario del pokémon no puede estar vacío.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El tipo secundario del pokémon no es válido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nivel del pokémon: ");
                nivel = input.nextInt();
                input.nextLine();
                if (nivel < 1) {
                    System.out.println("El nivel del pokémon no puede ser negativo.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nivel del pokémon no es válido.");
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

            System.out.println("Pokémon '" + nombre + "' agregado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar pokémon: " + e.getMessage());
        }
    }

    private static void leerPokemones() {
        String leerPokemonesSQL = "SELECT * FROM pokemons";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(leerPokemonesSQL);

            boolean hasPokemons = false;

            while (rs.next()) {
                hasPokemons = true;
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo_principal");
                String tipo_sec = rs.getString("tipo_secundario");
                int nivel = rs.getInt("nivel");

                System.out.println("ID: " + id + " | Nombre: " + nombre +
                        " | Tipo principal: " + tipo + " | Tipo secundario: " + tipo_sec + " | Nivel: " + nivel);
            }
            if (!hasPokemons) {
                System.out.println("No hay pokémons en la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokémons: " + e.getMessage());
        }
    }

    private static void actualizarPokemon() {
        int id = 0;
        String nombre;
        String tipo;
        String tipo_sec;
        int nivel;

        do {
            try {
                System.out.print("Ingrese el id del pokémon para ACTUALIZAR: ");
                id = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del pokémon no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existePokemonSQL = "SELECT * FROM pokemons WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existePokemonSQL);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    break;
                } else {
                    System.out.println("El pokémon con id " + id + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el pokémon existe: " + e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nuevo nombre del pokémon: ");
                nombre = input.nextLine();
                if (nombre.isBlank()) {
                    System.out.println("El nombre del pokémon no puede estar vacío.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nombre del pokémon no es válido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nuevo tipo principal del pokémon: ");
                tipo = input.nextLine();
                if (tipo.isBlank()) {
                    System.out.println("El tipo principal del pokémon no puede estar vacío.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El tipo principal del pokémon no es válido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nuevo tipo secundario del pokémon: ");
                tipo_sec = input.nextLine();
                if (tipo_sec.isBlank()) {
                    System.out.println("El tipo secundario del pokémon no puede estar vacío.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El tipo secundario del pokémon no es válido.");
                input.nextLine();
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el nuevo nivel del pokémon: ");
                nivel = input.nextInt();
                input.nextLine();
                if (nivel < 1) {
                    System.out.println("El nivel del pokémon no puede ser negativo.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nivel del pokémon no es válido.");
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
                System.out.println("Pokémon actualizado con exito.");
            } else {
                System.out.println("Pokémon no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar pokémon: " + e.getMessage());
        }
    }

    private static void eliminarPokemon() {
        int id = 0;

        do {
            try {
                System.out.print("Ingrese el id del pokémon para ELIMINAR: ");
                id = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del pokémon no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existePokemonSQL = "SELECT * FROM pokemons WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existePokemonSQL);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    break;
                } else {
                    System.out.println("El pokémon con id " + id + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el pokémon existe: " + e.getMessage());
            }
        } while (true);

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
                System.out.println("Pokémon a eliminar:");
                System.out.println("Nombre: " + nombre);
                System.out.println("Tipo principal: " + tipo);
                System.out.println("Tipo secundario: " + tipo_sec);
                System.out.println("Nivel: " + nivel);
                System.out.println("");

                System.out.print("¿Está seguro de eliminar este pokémon? (s/n): ");
                String confirmar = input.nextLine();

                System.out.println("");
                if (confirmar.equalsIgnoreCase("s")) {
                    String eliminarPokemonSQL = "DELETE FROM pokemons WHERE id = ?";

                    try {
                        pstmt = conn.prepareStatement(eliminarPokemonSQL);
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();

                        System.out.println("Pokémon eliminado con exito.");
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar pokémon: " + e.getMessage());
                    }
                } else {
                    System.out.println("Cancelado.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokémon: " + e.getMessage());
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
                    System.out.println("El nombre del entrenador no puede estar vacío.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nombre del entrenador no es válido.");
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

            System.out.println("Entrenador '" + nombre + "' agregado con exito.");
        } catch (SQLException e) {
            System.out.println("Error al agregar entrenador: " + e.getMessage());
        }
    }

    private static void listaEntrenadores() {
        String listaEntrenadoresSQL = "SELECT * FROM entrenadores";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(listaEntrenadoresSQL);

            boolean hasEntrenadores = false;

            while (rs.next()) {
                hasEntrenadores = true;
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String ciudad_origen = rs.getString("ciudad_origen");
                ArrayList<String> pokemons = new ArrayList<>();

                Statement stmtpk = conn.createStatement();
                ResultSet rspk = stmtpk.executeQuery("SELECT * FROM entrenador_pokemon WHERE entrenador_id = " + id);
                String pkString = "";

                while (rspk.next()) {
                    int pk_id = rspk.getInt("pokemon_id");

                    Statement stmtpk2 = conn.createStatement();
                    ResultSet rspk2 = stmtpk2.executeQuery("SELECT * FROM pokemons WHERE id = " + pk_id);

                    while (rspk2.next()) {
                        String pk_nombre = rspk2.getString("nombre");
                        pokemons.add(pk_nombre);
                    }
                }

                if (!pokemons.isEmpty()) {
                    pkString = String.join(", ", pokemons);
                } else {
                    pkString = "Ninguno";
                }

                System.out.println("ID: " + id + " | Nombre: " + nombre +
                        " | Ciudad de origen: " + ciudad_origen + " | Pokémons: " + pkString);
            }
            if (!hasEntrenadores) {
                System.out.println("No hay entrenadores en la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error al leer entrenadores: " + e.getMessage());
        }
    }

    private static void actualizarEntrenador() {
        int id;
        String nombre;
        String ciudad;

        do {
            try {
                System.out.print("Ingrese el id del entrenador para ACTUALIZAR: ");
                id = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

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
                    System.out.println("El nombre del entrenador no puede estar vacío.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("El nombre del entrenador no es válido.");
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
            try {
                System.out.print("Ingrese el id del entrenador para ELIMINAR: ");
                id = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

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
        int id_ent = 0;
        int id_pok = 0;
        String nombre_entrenador = "";
        String nombre_pokemon = "";

        do {
            try {

                System.out.print("Ingrese el id del entrenador para ASIGNARLE un pokémon: ");
                id_ent = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id_ent);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_entrenador = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El entrenador con id " + id_ent + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el id del pokémon para ASIGNARLO al entrenador: ");
                id_pok = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del pokémon no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existePokemonSQL = "SELECT * FROM pokemons WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existePokemonSQL);
                pstmt.setInt(1, id_pok);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_pokemon = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El pokémon con id " + id_pok + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el pokémon existe: " + e.getMessage());
            }
        } while (true);

        String asignarPokemonSQL = "INSERT INTO entrenador_pokemon (entrenador_id, pokemon_id) VALUES (?, ?)";
        // String asignarPokemonSQL = "INSERT INTO entrenador_pokemon (entrenador_id,
        // pokemon_id, fecha) VALUES (?, ?, ?)";
        System.out.println("");

        try {
            PreparedStatement pstmt = conn.prepareStatement(asignarPokemonSQL);
            pstmt.setInt(1, id_ent);
            pstmt.setInt(2, id_pok);
            pstmt.executeUpdate();

            System.out.println(
                    "Pokémon '" + nombre_pokemon + "' ASIGNADO al entrenador '" + nombre_entrenador + "' con exito.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println(
                    "El entrenador '" + nombre_entrenador + "' YA tiene ASIGNADO el pokémon '" + nombre_pokemon + "'.");
        } catch (SQLException e) {
            System.out.println("Error al asignar pokémon: " + e.getMessage());
        }
    }

    private static void eliminarPokemonDeEntrenador() {
        int id_ent = 0;
        int id_pok = 0;
        String nombre_entrenador = "";
        String nombre_pokemon = "";

        do {
            try {
                System.out.print("Ingrese el id del entrenador para DESASIGNARLE un pokémon: ");
                id_ent = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id_ent);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_entrenador = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El entrenador con id " + id_ent + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el id del pokémon para DESASIGNARLO del entrenador: ");
                id_pok = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del pokémon no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existePokemonSQL = "SELECT * FROM pokemons WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existePokemonSQL);
                pstmt.setInt(1, id_pok);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_pokemon = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El pokémon con id " + id_pok + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el pokémon existe: " + e.getMessage());
            }
        } while (true);

        String eliminarPokemonSQL = "DELETE FROM entrenador_pokemon WHERE entrenador_id = ? AND pokemon_id = ?";
        System.out.println("");

        try {
            PreparedStatement pstmt = conn.prepareStatement(eliminarPokemonSQL);
            pstmt.setInt(1, id_ent);
            pstmt.setInt(2, id_pok);

            if (pstmt.executeUpdate() == 0) {
                System.out.println(
                        "El entrenador '" + nombre_entrenador + "' NO tiene ASIGNADO el pokémon '" + nombre_pokemon
                                + "'.");
                        "El entrenador '" + nombre_entrenador + "' NO tiene ASIGNADO el pokémon '" + nombre_pokemon
                                + "'.");
                System.out.println("No se ha realizado ninguna modificación.");
            } else {
                System.out.println(
                        "Pokémon '" + nombre_pokemon + "' DESASIGNADO del entrenador '" + nombre_entrenador
                                + "' con exito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar pokémon: " + e.getMessage());
        }
    }

    private static void registrarBatalla() {
        int id_ent_1 = 0;
        String nombre_entrenador_1 = "";
        int id_ent_2 = 0;
        String nombre_entrenador_2 = "";
        int id_ganandor = 0;
        int id_perdedor = 0;

        do {
            try {
                System.out.print("Ingrese el id del PRIMER entrenador para la batalla: ");
                id_ent_1 = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id_ent_1);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_entrenador_1 = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El entrenador con id " + id_ent_1 + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.print("Ingrese el id del SEGUNDO entrenador para la batalla: ");
                id_ent_2 = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id_ent_2);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_entrenador_2 = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El entrenador con id " + id_ent_2 + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        if (id_ent_1 == id_ent_2) {
            System.out.println("El entrenador no puede enfrentarse a sí mismo.");
            return;
        }

        System.out
                .println("Batalla entre los entrenadores: " + nombre_entrenador_1 + " y " + nombre_entrenador_2 + ".");
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
            id_ganandor = id_ent_1;
            id_perdedor = id_ent_2;
            System.out.println(
                    "El ganador es: " + nombre_entrenador_1 + " y el perdedor es: " + nombre_entrenador_2 + ".");
        } else {
            id_ganandor = id_ent_2;
            id_perdedor = id_ent_1;
            System.out.println(
                    "El ganador es: " + nombre_entrenador_2 + " y el perdedor es: " + nombre_entrenador_1 + ".");
        }

        String registrarBatallaSQL = "INSERT INTO batallas (ganador_id, perdedor_id, fecha) VALUES (?, ?, ?)";
        System.out.println("");

        try {
            PreparedStatement pstmt = conn.prepareStatement(registrarBatallaSQL);
            pstmt.setInt(1, id_ganandor);
            pstmt.setInt(2, id_perdedor);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.executeUpdate();

            System.out.println("Batalla registrada con exito.");
        } catch (SQLException e) {
            System.out.println("Error al registrar batalla: " + e.getMessage());
        }
    }

    private static void verHistorialBatallas() {
        String leerBatallas = "SELECT * FROM batallas";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(leerBatallas);

            while (rs.next()) {
                /* int id = rs.getInt("id"); */
                String fecha = rs.getString("fecha");
                String id_ganador = rs.getString("ganador_id");
                String id_perdedor = rs.getString("perdedor_id");

                String sqlEntrenador = "SELECT * FROM entrenadores WHERE id = ?";

                String nombre_ganador = "", nombre_perdedor = "";

                PreparedStatement pstmt = conn.prepareStatement(sqlEntrenador);
                pstmt.setInt(1, Integer.parseInt(id_ganador));
                ResultSet rsGanador = pstmt.executeQuery();

                if (rsGanador.next()) {
                    nombre_ganador = rsGanador.getString("nombre");
                } else {
                    System.out.println("El entrenador con id " + id_ganador + " no existe.");
                }

                pstmt = conn.prepareStatement(sqlEntrenador);
                pstmt.setInt(1, Integer.parseInt(id_perdedor));
                ResultSet rsPerdedor = pstmt.executeQuery();

                if (rsPerdedor.next()) {
                    nombre_perdedor = rsPerdedor.getString("nombre");
                } else {
                    System.out.println("El entrenador con id " + id_perdedor + " no existe.");
                }

                System.out.println("Fecha: " + fecha +
                        ", Ganador: " + nombre_ganador + ", Perdedor: " + nombre_perdedor);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer las batallas: " + e.getMessage());
        }
    }

    private static void obtenerPokemonesDeEntrenador() {
        int id_ent = 0;
        String nombre_entrenador = "";

        do {
            try {
                System.out.print("Ingrese el ID del ENTRENADOR para la consulta: ");
                id_ent = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id_ent);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_entrenador = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El entrenador con id " + id_ent + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        System.out.println("Los pokémon de " + nombre_entrenador + " son: ");
        System.out.println();

        String sql = "SELECT p.id, p.nombre, p.tipo_principal, p.tipo_secundario, p.nivel FROM pokemons p "
                +
                "INNER JOIN entrenador_pokemon ep ON p.id = ep.pokemon_id WHERE ep.entrenador_id = ?";

        boolean existenPokemones = false;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_ent);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                existenPokemones = true;
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");

                System.out.println("ID: " + id + " | Nombre: " + nombre);
            }

            if (!existenPokemones) {
                System.out.println("No tiene pokémons.");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer pokémons: " + e.getMessage());
        }
    }

    private static void obtenerHistorialBatallasDeEntrenador() {
        int id_ent = 0;
        String nombre_entrenador = "";

        do {
            try {
                System.out.print("Ingrese el ID del ENTRENADOR para la consulta: ");
                id_ent = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id_ent);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_entrenador = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El entrenador con id " + id_ent + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        System.out.println("Las batallas de " + nombre_entrenador + " son: ");
        System.out.println();

        String sql = "SELECT b.id, b.ganador_id, b.perdedor_id, b.fecha FROM batallas b "
                +
                "INNER JOIN entrenadores e ON b.ganador_id = e.id OR b.perdedor_id = e.id WHERE e.id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_ent);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                /* int id = rs.getInt("id"); */
                String ganador_id = rs.getString("ganador_id");
                String perdedor_id = rs.getString("perdedor_id");
                String fecha = rs.getString("fecha");

                String sqlEntrenador = "SELECT * FROM entrenadores WHERE id = ?";

                String nombre_ganador = "", nombre_perdedor = "";

                PreparedStatement pstmt2 = conn.prepareStatement(sqlEntrenador);
                pstmt2.setInt(1, Integer.parseInt(ganador_id));
                ResultSet rsGanador = pstmt2.executeQuery();

                if (rsGanador.next()) {
                    nombre_ganador = rsGanador.getString("nombre");
                } else {
                    System.out.println("El entrenador con id " + ganador_id + " no existe.");
                }

                pstmt2 = conn.prepareStatement(sqlEntrenador);
                pstmt2.setInt(1, Integer.parseInt(perdedor_id));
                ResultSet rsPerdedor = pstmt2.executeQuery();

                if (rsPerdedor.next()) {
                    nombre_perdedor = rsPerdedor.getString("nombre");
                } else {
                    System.out.println("El entrenador con id " + perdedor_id + " no existe.");
                }

                System.out.println("Fecha: " + fecha +
                        " | Ganador: " + nombre_ganador + " (" + ganador_id + ") y Perdedor: " + nombre_perdedor
                        + " (" + perdedor_id + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error al leer batallas: " + e.getMessage());
        }
    }

    private static void obtenerEstadisticas() {
        int id_ent = 0;
        String nombre_entrenador = "";

        do {
            try {
                System.out.print("Ingrese el ID del ENTRENADOR para ver las estadísticas: ");
                id_ent = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("El id del entrenador no es válido. Debe ser un número.");
                input.nextLine();
                continue;
            }

            String existeEntrenadorSQL = "SELECT * FROM entrenadores WHERE id = ?";
            try {
                PreparedStatement pstmt = conn.prepareStatement(existeEntrenadorSQL);
                pstmt.setInt(1, id_ent);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    nombre_entrenador = rs.getString("nombre");
                    break;
                } else {
                    System.out.println("El entrenador con id " + id_ent + " no existe.");
                }
            } catch (SQLException e) {
                System.out.println("Error al comprobar si el entrenador existe: " + e.getMessage());
            }
        } while (true);

        String sql = "SELECT COUNT(*) AS batallas_ganadas FROM batallas WHERE ganador_id = ?";
        String sql2 = "SELECT COUNT(*) AS batallas_perdidas FROM batallas WHERE perdedor_id = ?";

        try {
            int batallas_ganadas = 0;
            int batallas_perdidas = 0;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id_ent);
            ResultSet rs = pstmt.executeQuery();

            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1, id_ent);
            ResultSet rs2 = pstmt2.executeQuery();

            while (rs.next() && rs2.next()) {
                batallas_ganadas = rs.getInt("batallas_ganadas");
                batallas_perdidas = rs2.getInt("batallas_perdidas");
            }

            System.out.println("Estadísticas de " + nombre_entrenador + ":");
            System.out.println();
            System.out.println("Batallas ganadas: " + batallas_ganadas);
            System.out.println("Batallas perdidas: " + batallas_perdidas);

        } catch (SQLException e) {
            System.out.println("Error al leer estadísticas: " + e.getMessage());
        }

    }
}