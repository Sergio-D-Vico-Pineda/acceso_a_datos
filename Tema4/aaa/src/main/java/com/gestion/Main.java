package com.gestion;

import com.gestion.dao.*;
import com.gestion.entities.*;
import com.gestion.utils.HibernateUtil;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private static PropietarioDAO propietarioDAO = new PropietarioDAO();
    // private static TipoVehiculoDAO tipoVehiculoDAO = new TipoVehiculoDAO();
    private static HistorialMantenimientoDAO historialMantenimientoDAO = new HistorialMantenimientoDAO();
    private static boolean salir = false;

    public static void main(String[] args) {
        while (!salir) {
            System.out.println("1. Registrar un Vehículo");
            System.out.println("2. Registrar un Propietario");
            System.out.println("3. Mostrar Todos los Vehículos");
            System.out.println("4. Buscar un Vehículo por Matrícula");
            System.out.println("5. Registrar un Mantenimiento");
            System.out.println("6. Listar Historial de Mantenimientos de un Vehículo");
            System.out.println("7. Actualizar Precio de un Vehículo");
            System.out.println("8. Eliminar un Vehículo");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = input.nextInt();
            } catch (Exception e) {
                opcion = -1;
            }
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> registrarVehiculo();
                case 2 -> registrarPropietario();
                case 3 -> mostrarTodosLosVehiculos();
                case 4 -> buscarVehiculoPorMatricula();
                case 5 -> registrarMantenimiento();
                case 6 -> listarHistorialMantenimientos();
                case 7 -> actualizarPrecioVehiculo();
                case 8 -> eliminarVehiculo();
                case 9 -> salir();
                default -> opcionInvalida();
            }

            if (!salir) {
                System.out.println("");
                System.out.print("Presiona ENTER para continuar...");
                System.out.println("");
            }
        }
    }

    private static void opcionInvalida() {
        System.out.println("Opción inválida.");
    }

    private static void salir() {
        System.out.println("Hasta luego!");
        salir = true;
        HibernateUtil.shutdown();
    }

    private static void registrarVehiculo() {
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Año de fabricación: ");
        int añoFabricacion = scanner.nextInt();
        System.out.print("Precio: ");
        BigDecimal precio = scanner.nextBigDecimal();
        scanner.nextLine(); // Limpiar buffer

        System.out.print("¿Desea guardar en MySQL? (s/n): ");
        char guardarEnBD = scanner.nextLine().charAt(0);
        if (guardarEnBD == 's') {
            guardarVehiculoEnBD(matricula, marca, modelo, añoFabricacion, precio);
        } else {
            System.out.print("¿Desea guardar en TXT? (s/n): ");
            char guardarEnTXT = scanner.nextLine().charAt(0);
            if (guardarEnTXT == 's') {
                guardarVehiculoEnTXT(matricula, marca, modelo, añoFabricacion, precio);
            } else {
                System.out.print("¿Desea guardar en XML? (s/n): ");
                char guardarEnXML = scanner.nextLine().charAt(0);
                if (guardarEnXML == 's') {
                    guardarVehiculoEnXML(matricula, marca, modelo, añoFabricacion, precio);
                }
            }
        }
    }

    private static void guardarVehiculoEnBD(String matricula, String marca, String modelo, int añoFabricacion,
            BigDecimal precio) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMatricula(matricula);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setAñoFabricacion(añoFabricacion);
        vehiculo.setPrecio(precio);

        vehiculoDAO.save(vehiculo);
        System.out.println("Vehículo guardado en BD.");
    }

    private static void guardarVehiculoEnTXT(String matricula, String marca, String modelo, int añoFabricacion,
            BigDecimal precio) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("vehiculos.txt", true))) {
            writer.println(matricula + "," + marca + "," + modelo + "," + añoFabricacion + "," + precio);
            System.out.println("Vehículo guardado en TXT.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarVehiculoEnXML(String matricula, String marca, String modelo, int añoFabricacion,
            BigDecimal precio) {
        try (FileWriter fileWriter = new FileWriter("vehiculos.xml", true)) {
            fileWriter.write("<vehiculo>\n");
            fileWriter.write("    <matricula>" + matricula + "</matricula>\n");
            fileWriter.write("    <marca>" + marca + "</marca>\n");
            fileWriter.write("    <modelo>" + modelo + "</modelo>\n");
            fileWriter.write("    <añoFabricacion>" + añoFabricacion + "</añoFabricacion>\n");
            fileWriter.write("    <precio>" + precio + "</precio>\n");
            fileWriter.write("</vehiculo>\n");
            System.out.println("Vehículo guardado en XML.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void registrarPropietario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Propietario propietario = new Propietario();
        propietario.setNombre(nombre);
        propietario.setApellido(apellido);
        propietario.setDni(dni);
        propietario.setTelefono(telefono);

        propietarioDAO.save(propietario);
        System.out.println("Propietario guardado en BD.");
    }

    private static void mostrarTodosLosVehiculos() {
        List<Vehiculo> vehiculos = vehiculoDAO.getAll();
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }

    private static void buscarVehiculoPorMatricula() {
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        Vehiculo vehiculo = vehiculoDAO.get(matricula);
        if (vehiculo != null) {
            System.out.println(vehiculo);
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    private static void registrarMantenimiento() {
        System.out.print("Matrícula del vehículo: ");
        String matricula = scanner.nextLine();
        System.out.print("Fecha (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Coste: ");
        BigDecimal coste = scanner.nextBigDecimal();
        scanner.nextLine(); // Limpiar buffer

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = dateFormat.parse(fechaStr);

            Vehiculo vehiculo = vehiculoDAO.get(matricula);
            if (vehiculo != null) {
                HistorialMantenimiento mantenimiento = new HistorialMantenimiento();
                mantenimiento.setVehiculo(vehiculo);
                mantenimiento.setFecha(fecha);
                mantenimiento.setDescripcion(descripcion);
                mantenimiento.setCoste(coste);

                historialMantenimientoDAO.save(mantenimiento);
                System.out.println("Mantenimiento registrado en BD.");
            } else {
                System.out.println("Vehículo no encontrado.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void listarHistorialMantenimientos() {
        System.out.print("Matrícula del vehículo: ");
        String matricula = scanner.nextLine();

        List<HistorialMantenimiento> historiales = historialMantenimientoDAO.getByVehiculo(matricula);
        if (historiales != null && !historiales.isEmpty()) {
            for (HistorialMantenimiento mantenimiento : historiales) {
                System.out.println(mantenimiento);
            }
        } else {
            System.out.println("No se encontraron mantenimientos para este vehículo.");
        }
    }

    private static void actualizarPrecioVehiculo() {
        System.out.print("Matrícula del vehículo: ");
        String matricula = scanner.nextLine();
        System.out.print("Nuevo precio: ");
        BigDecimal nuevoPrecio = scanner.nextBigDecimal();
        scanner.nextLine(); // Limpiar buffer

        Vehiculo vehiculo = vehiculoDAO.get(matricula);
        if (vehiculo != null) {
            vehiculo.setPrecio(nuevoPrecio);
            vehiculoDAO.update(vehiculo);
            System.out.println("Precio actualizado.");
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    private static void eliminarVehiculo() {
        System.out.print("Matrícula del vehículo: ");
        String matricula = scanner.nextLine();

        Vehiculo vehiculo = vehiculoDAO.get(matricula);
        if (vehiculo != null) {
            if (vehiculo.getHistorialesMantenimiento() != null && !vehiculo.getHistorialesMantenimiento().isEmpty()) {
                System.out.print("Este vehículo tiene mantenimientos registrados. ¿Desea eliminarlo? (s/n): ");
                char confirmacion = scanner.nextLine().charAt(0);
                if (confirmacion == 's') {
                    vehiculoDAO.delete(vehiculo);
                    System.out.println("Vehículo eliminado.");
                } else {
                    System.out.println("Eliminación cancelada.");
                }
            } else {
                vehiculoDAO.delete(vehiculo);
                System.out.println("Vehículo eliminado.");
            }
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }
}