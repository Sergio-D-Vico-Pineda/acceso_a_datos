package com.example;

import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        // Creo la conexión a la base de datos si no se puede abrir, finalizo el programa
        if (!DBConnection.crearConexion()) {
            salirPrograma();
            return;
        }

        do {
            System.out.println();
            System.out.println("Ejercicio Durisimo para Aprobar - Acceso a datos - Sergio David Vico Pineda");
            System.out.println();
            // Por si se ha podido iniciar la conexion pero hay algun problema lo vuelvo a comprobar, si no se puede, finalizo el programa
            if (!DBConnection.status()) {
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
            }

            System.out.println();
            input.nextLine(); // Este tipo de next() y nextLine() son para limpiar el intro del los nextInt o nextDouble

            switch (opcion) {
                case 0 -> salirPrograma();
                case 1 -> agregarPokemon();
                case 2 -> leerPokemons();
                case 3 -> actualizarPokemon();
                case 4 -> eliminarPokemon();

                case 5 -> agregarEntrenador();
                case 6 -> leerEntrenadores();
                case 7 -> actualizarEntrenador();
                case 8 -> eliminarEntrenador();

                case 9 -> asignarPokemonEntrenador();
                case 10 -> desasignarPokemonEntrenador();
                case 11 -> registrarBatalla();
                case 12 -> historialBatallas();

                case 13 -> obtenerPokemonsDeEntrenador();
                case 14 -> obtenerHistorialBatallasDeEntrenador();
                case 15 -> obtenerEstadisticas();

                default -> OpcionInvalida();
            }

            // Esta condicion es para que al salir del programa no se imprima el mensaje
            if (opcion != 0) {
                System.out.println("");
                System.out.print("Presiona ENTER para continuar...");
                input.nextLine();
            }

        } while (opcion != 0);
    }

    private static void OpcionInvalida() {
        System.out.println("Opción invalida.");
    }

    public static void salirPrograma() {
        System.out.println("Programa finalizado. Bye.");
        System.out.println("");
        // Compruebo si la base de está abierta y la cierro
        if (DBConnection.con() != null)
            DBConnection.cerrarConexion();
    }

    private static void agregarPokemon() {
        String nombre, tipo, tipoSec;
        int nivel;

        System.out.println("Agregando un nuevo pokémon: ");
        System.out.println("");

        // Este metodo es para que el usuario introduzca datos, el parametro es que dato se requiere
        nombre = Pokemon.escribir("nombre");
        tipo = Pokemon.escribir("tipo");
        tipoSec = Pokemon.escribir("tipo secundario");
        // Aqui hay una variación para Ints
        nivel = Pokemon.escribirInt("nivel");

        Pokemon.agregarPokemon(nombre, tipo, tipoSec, nivel);
    }

    private static void actualizarPokemon() {
        System.out.println("Actualizando un pokémon: ");
        System.out.println("");

        if (!Pokemon.hayPokemons()) {
            System.out.println("No hay pokémons en la base de datos.");
            return;
        }

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
        System.out.println("Eliminando un pokémon: ");
        System.out.println("");

        if (!Pokemon.hayPokemons()) {
            System.out.println("No hay pokémons en la base de datos.");
            return;
        }

        int id = 0;

        id = Pokemon.escribirInt("id a ELIMINAR");
        System.out.println("");

        if (!Pokemon.existePokemon(id))
            return;

        Pokemon.eliminarPokemon(id);
    }

    private static void agregarEntrenador() {
        String nombre, ciudad;

        System.out.println("Agregando un nuevo entrenador: ");
        System.out.println("");

        nombre = Entrenador.escribir("nombre");
        ciudad = Entrenador.escribir("ciudad");

        Entrenador.agregarEntrenador(nombre, ciudad);
    }

    private static void actualizarEntrenador() {

        System.out.println("Actualizando un entrenador: ");
        System.out.println("");

        if (!Entrenador.hayEntrenadores()) {
            System.out.println("No hay entrenadores en la base de datos.");
            return;
        }

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

        System.out.println("Eliminando un entrenador: ");
        System.out.println("");

        if (!Entrenador.hayEntrenadores()) {
            System.out.println("No hay entrenadores en la base de datos.");
            return;
        }

        int id;

        id = Entrenador.escribirInt("id a ELIMINAR");
        System.out.println("");

        if (!Entrenador.existeEntrenador(id))
            return;

        Entrenador.eliminarEntrenador(id);
    }

    private static void asignarPokemonEntrenador() {
        int idEnt = 0, idPok = 0;

        System.out.print("Ingrese el id del entrenador para ASIGNARLE un pokémon: ");
        idEnt = Entrenador.escribirInt("id", false);

        if (!Entrenador.existeEntrenador(idEnt)) {
            return;
        }

        System.out.print("Ingrese el id del pokémon para ASIGNARLO al entrenador: ");
        idPok = Pokemon.escribirInt("id", false);

        if (!Pokemon.existePokemon(idPok)) {
            return;
        }

        Entrenador.asignarEntrenadorPokemon(idEnt, idPok);
    }

    private static void desasignarPokemonEntrenador() {
        int idEnt = 0, idPok = 0;

        System.out.print("Ingrese el id del entrenador para DESASIGNARLE un pokémon: ");
        idEnt = Entrenador.escribirInt("id", false);

        if (!Entrenador.existeEntrenador(idEnt)) {
            return;
        }

        System.out.print("Ingrese el id del pokémon para DESASIGNARLO al entrenador: ");
        idPok = Pokemon.escribirInt("id", false);

        if (!Pokemon.existePokemon(idPok)) {
            return;
        }

        Entrenador.desasignarEntrenadorPokemon(idEnt, idPok);
    }

    private static void registrarBatalla() {

        System.out.println("Registrando una batalla: ");
        System.out.println("");

        if (!Entrenador.hayEntrenadores()) {
            System.out.println("No hay entrenadores en la base de datos.");
            return;
        }

        int idEnt1, idEnt2;

        System.out.print("Ingrese el id del PRIMER entrenador para la batalla: ");
        idEnt1 = Entrenador.escribirInt("id", false);

        if (!Entrenador.existeEntrenador(idEnt1))
            return;

        System.out.print("Ingrese el id del SEGUNDO entrenador para la batalla: ");
        idEnt2 = Entrenador.escribirInt("id", false);

        if (!Entrenador.existeEntrenador(idEnt2))
            return;

        Batalla.registrarBatalla(idEnt1, idEnt2);
    }

    private static void historialBatallas() {
        System.out.println("Mostrando historial de batallas: ");
        System.out.println("");

        Batalla.historial();
    }

    private static void obtenerPokemonsDeEntrenador() {

        System.out.println("Mostrando pokémons de un entrenador: ");
        System.out.println("");

        if (!Entrenador.hayEntrenadores()) {
            System.out.println("No hay entrenadores en la base de datos.");
            return;
        }

        int idEnt = Entrenador.escribirInt("id");
        System.out.println("");

        if (!Entrenador.existeEntrenador(idEnt)) {
            return;
        }

        Entrenador.leerPokemonsEntrenador(idEnt);
    }

    private static void obtenerHistorialBatallasDeEntrenador() {

        System.out.println("Mostrando el historial de batallas de un entrenador: ");
        System.out.println("");

        if (!Entrenador.hayEntrenadores()) {
            System.out.println("No hay entrenadores en la base de datos.");
            return;
        }

        int idEnt = Entrenador.escribirInt("id");
        System.out.println("");

        if (!Entrenador.existeEntrenador(idEnt))
            return;

        Batalla.historial(idEnt);
    }

    private static void obtenerEstadisticas() {

        System.out.println("Mostrando estadísticas de un entrenador: ");
        System.out.println("");

        if (!Entrenador.hayEntrenadores()) {
            System.out.println("No hay entrenadores en la base de datos.");
            return;
        }

        int idEnt = Entrenador.escribirInt("id");

        if (!Entrenador.existeEntrenador(idEnt))
            return;

        Entrenador.estadisticas(idEnt);
    }
}