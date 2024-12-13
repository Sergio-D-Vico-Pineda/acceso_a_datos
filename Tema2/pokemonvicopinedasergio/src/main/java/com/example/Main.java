package com.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        if (!DBConnection.crearConexion()) {
            salirPrograma();
            return;
        }

        do {
            System.out.println();
            System.out.println("Ejercicio Durisimo para Aprobar - Acceso a datos - Sergio David Vico Pineda");
            System.out.println();
            try {
                System.out
                        .println("Conexión exitosa a la base de datos: "
                                + DBConnection.con().getMetaData().getDatabaseProductName());
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
            System.out.println("1. Agregar pokémon. DONE");
            System.out.println("2. Listar pokémons. DONE");
            System.out.println("3. Actualizar pokémon. DONE");
            System.out.println("4. Eliminar pokémon. DONE");
            System.out.println();
            System.out.println("Entrenadores:");
            System.out.println("5. Agregar entrenador. DONE");
            System.out.println("6. Listar entrenadores. DONE");
            System.out.println("7. Actualizar entrenador. DONE");
            System.out.println("8. Eliminar entrenador. DONE");
            System.out.println();
            System.out.println("Opciones:");
            System.out.println("9. Asignar pokémon a un entrenador. DONE");
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
                case 2 -> Pokemon.leerPokemones();
                case 3 -> actualizarPokemon();
                case 4 -> eliminarPokemon();

                case 5 -> agregarEntrenador();
                case 6 -> Entrenador.leerEntrenadores();
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
        System.out.println("Opción invalida.");
    }

    private static void salirPrograma() {
        System.out.println("Programa finalizado. Bye.");
        System.out.println("");
        if (DBConnection.con() != null)
            DBConnection.cerrarConexion();
    }

    private static void agregarPokemon() {
        String nombre, tipo, tipo_sec;
        int nivel;

        nombre = Pokemon.escribir("nombre");
        tipo = Pokemon.escribir("tipo");
        tipo_sec = Pokemon.escribir("tipo secundario");
        nivel = Pokemon.escribirInt("nivel");

        Pokemon.agregarPokemon(nombre, tipo, tipo_sec, nivel);
    }

    private static void actualizarPokemon() {
        int id;
        String nombre, tipo, tipoSec;
        int nivel;

        id = Pokemon.escribirInt("id a ACTUALIZAR");

        if (!Pokemon.existePokemon(id))
            return;

        System.out.println("");
        System.out.println("Vas a actualizar el siguiente pokémon:");
        Pokemon.info(id);
        System.out.println("");

        nombre = Pokemon.escribir("nombre");
        tipo = Pokemon.escribir("tipo");
        tipoSec = Pokemon.escribir("tipo secundario");
        nivel = Pokemon.escribirInt("nivel");

        Pokemon.actualizarPokemon(id, nombre, tipo, tipoSec, nivel);
    }

    private static void eliminarPokemon() {
        int id = 0;

        id = Pokemon.escribirInt("id a ELIMINAR");
        System.out.println("");

        if (!Pokemon.existePokemon(id))
            return;

        Pokemon.eliminarPokemon(id);
    }

    private static void agregarEntrenador() {
        String nombre, ciudad;

        nombre = Entrenador.escribir("nombre");
        ciudad = Entrenador.escribir("ciudad");

        Entrenador.agregarEntrenador(nombre, ciudad);
    }

    private static void actualizarEntrenador() {
        int id;
        String nombre, ciudad;

        id = Entrenador.escribirInt("id a ACTUALIZAR");

        if (!Entrenador.existeEntrenador(id))
            return;

        System.out.println("");
        System.out.println("Vas a actualizar el siguiente entrenador:");
        Entrenador.info(id);
        System.out.println("");

        nombre = Entrenador.escribir("nombre");
        ciudad = Entrenador.escribir("ciudad");

        Entrenador.actualizarEntrenador(id, nombre, ciudad);
    }

    private static void eliminarEntrenador() {
        int id;

        id = Entrenador.escribirInt("id a ELIMINAR");

        if (!Entrenador.existeEntrenador(id))
            return;

        Entrenador.eliminarEntrenador(id);
    }

    private static void asignarPokemonAEntrenador() {
        int idEnt = 0, idPok = 0;

        System.out.print("Ingrese el id del entrenador para ASIGNARLE un pokémon: ");
        idEnt = Entrenador.escribirInt("id");

        if (!Entrenador.existeEntrenador(idEnt)) {
            return;
        }

        System.out.print("Ingrese el id del pokémon para ASIGNARLO al entrenador: ");
        idPok = Pokemon.escribirInt("id");

        if (!Pokemon.existePokemon(idPok)) {
            return;
        }

        Entrenador.asignarEntrenadorPokemon(idEnt, idPok);        
    }

    private static void eliminarPokemonDeEntrenador() {
        int idEnt = 0, idPok = 0;

        System.out.print("Ingrese el id del entrenador para DESASIGNARLE un pokémon: ");
        idEnt = Entrenador.escribirInt("id");

        if (!Entrenador.existeEntrenador(idEnt)) {
            return;
        }

        System.out.print("Ingrese el id del pokémon para DESASIGNARLO al entrenador: ");
        idPok = Pokemon.escribirInt("id");

        if (!Pokemon.existePokemon(idPok)) {
            return;
        }

        Entrenador.desasignarEntrenadorPokemon(idEnt, idPok);
    }

    private static void registrarBatalla() {
        int id_ent_1 = 0, id_ent_2 = 0;
        String nombre_entrenador_1 = "", nombre_entrenador_2 = "";
        int id_ganandor = 0, id_perdedor = 0;

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
                PreparedStatement pstmt = DBConnection.con().prepareStatement(existeEntrenadorSQL);
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
                PreparedStatement pstmt = DBConnection.con().prepareStatement(existeEntrenadorSQL);
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
            PreparedStatement pstmt = DBConnection.con().prepareStatement(registrarBatallaSQL);
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
            Statement stmt = DBConnection.con().createStatement();
            ResultSet rs = stmt.executeQuery(leerBatallas);

            while (rs.next()) {
                /* int id = rs.getInt("id"); */
                String fecha = rs.getString("fecha");
                String id_ganador = rs.getString("ganador_id");
                String id_perdedor = rs.getString("perdedor_id");

                String sqlEntrenador = "SELECT * FROM entrenadores WHERE id = ?";

                String nombre_ganador = "", nombre_perdedor = "";

                PreparedStatement pstmt = DBConnection.con().prepareStatement(sqlEntrenador);
                pstmt.setInt(1, Integer.parseInt(id_ganador));
                ResultSet rsGanador = pstmt.executeQuery();

                if (rsGanador.next()) {
                    nombre_ganador = rsGanador.getString("nombre");
                } else {
                    System.out.println("El entrenador con id " + id_ganador + " no existe.");
                }

                pstmt = DBConnection.con().prepareStatement(sqlEntrenador);
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
                PreparedStatement pstmt = DBConnection.con().prepareStatement(existeEntrenadorSQL);
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
            PreparedStatement pstmt = DBConnection.con().prepareStatement(sql);
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
                PreparedStatement pstmt = DBConnection.con().prepareStatement(existeEntrenadorSQL);
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
            PreparedStatement pstmt = DBConnection.con().prepareStatement(sql);
            pstmt.setInt(1, id_ent);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                /* int id = rs.getInt("id"); */
                String ganador_id = rs.getString("ganador_id");
                String perdedor_id = rs.getString("perdedor_id");
                String fecha = rs.getString("fecha");

                String sqlEntrenador = "SELECT * FROM entrenadores WHERE id = ?";

                String nombre_ganador = "", nombre_perdedor = "";

                PreparedStatement pstmt2 = DBConnection.con().prepareStatement(sqlEntrenador);
                pstmt2.setInt(1, Integer.parseInt(ganador_id));
                ResultSet rsGanador = pstmt2.executeQuery();

                if (rsGanador.next()) {
                    nombre_ganador = rsGanador.getString("nombre");
                } else {
                    System.out.println("El entrenador con id " + ganador_id + " no existe.");
                }

                pstmt2 = DBConnection.con().prepareStatement(sqlEntrenador);
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
                PreparedStatement pstmt = DBConnection.con().prepareStatement(existeEntrenadorSQL);
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

            PreparedStatement pstmt = DBConnection.con().prepareStatement(sql);
            pstmt.setInt(1, id_ent);
            ResultSet rs = pstmt.executeQuery();

            PreparedStatement pstmt2 = DBConnection.con().prepareStatement(sql2);
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