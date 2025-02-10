package com.gestion;

import com.gestion.dao.*;
import com.gestion.entities.*;
import com.gestion.utils.HibernateUtil;
import com.gestion.utils.XmlGenerator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static XmlGenerator xmlGenerator = new XmlGenerator("datos.xml");
    private static Scanner input = new Scanner(System.in);
    private static VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private static PropietarioDAO propietarioDAO = new PropietarioDAO();
    private static TipoVehiculoDAO tipoVehiculoDAO = new TipoVehiculoDAO();
    private static HistorialMantenimientoDAO historialMantenimientoDAO = new HistorialMantenimientoDAO();
    private static boolean salir = false;

    public static void main(String[] args) {
        int opcion = 0;
        HibernateUtil.seed();

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
                System.out.println("");
            } catch (Exception e) {
                opcion = -1;
            }
            input.nextLine(); // Limpiar buffer

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
                input.nextLine();
                System.out.println("");
            }
        }
    }

    private static void opcionInvalida() {
        System.out.println("Opción inválida.");
    }

    private static void salir() {
        System.out.println("Guardando en XML...");
        HibernateUtil.fromDbtoXml(xmlGenerator);
        System.out.println("XML guardado.");
        System.out.println("-------------");
        System.out.println("Hasta luego!");
        salir = true;
        HibernateUtil.shutdown();
    }

    private static void registrarVehiculo() {

        List<Propietario> propietarios = propietarioDAO.getAll();
        if (propietarios.isEmpty()) {
            System.out.println(
                    "No hay propietarios registrados. Debe registrar al menos un propietario antes de registrar un vehículo.");
            return;
        }

        System.out.print("Matrícula: ");
        String matricula = input.nextLine();
        System.out.print("Marca: ");
        String marca = input.nextLine();
        System.out.print("Modelo: ");
        String modelo = input.nextLine();
        System.out.print("Año de fabricación: ");
        int añoFabricacion = input.nextInt();
        System.out.print("Precio: ");
        BigDecimal precio = input.nextBigDecimal();
        input.nextLine(); // Limpiar buffer

        System.out.println("Propietarios disponibles:");
        for (Propietario propietario : propietarios) {
            System.out.println(
                    propietario.getIdPropietario() + ". " + propietario.getNombre() + " " + propietario.getApellido());
        }

        Propietario propietario = null;
        while (propietario == null) {
            System.out.print("ID del propietario (pon 0 para salir): ");
            int idPropietarioSeleccionado = input.nextInt();
            input.nextLine(); // Limpiar buffer
            if (idPropietarioSeleccionado == 0) {
                return;
            }
            propietario = propietarioDAO.get(idPropietarioSeleccionado);
            if (propietario == null) {
                System.out.println("El propietario con ID " + idPropietarioSeleccionado + " no existe.");
            }
        }

        System.out.println("Tipos de vehículos disponibles:");
        List<TipoVehiculo> tipos = tipoVehiculoDAO.getAll();
        for (TipoVehiculo tipo : tipos) {
            System.out.println(tipo.getIdTipo() + ". " + tipo.getTipo());
        }

        System.out.print("ID del tipo de vehículo: ");
        int idTipo = input.nextInt();
        input.nextLine(); // Limpiar buffer

        Vehiculo vehiculo = new Vehiculo(
                matricula,
                marca,
                modelo,
                añoFabricacion,
                precio,
                propietario,
                tipoVehiculoDAO.get(idTipo));

        // xmlGenerator.addOrUpdateVehiculo(vehiculo);
        vehiculoDAO.save(vehiculo);
        System.out.println("Vehículo guardado en BD.");
    }

    private static void registrarPropietario() {
        System.out.print("Nombre: ");
        String nombre = input.nextLine();
        System.out.print("Apellido: ");
        String apellido = input.nextLine();
        System.out.print("DNI: ");
        String dni = input.nextLine();
        System.out.print("Teléfono: ");
        String telefono = input.nextLine();

        Propietario propietario = new Propietario(
                nombre,
                apellido,
                dni,
                telefono);

        // xmlGenerator.addOrUpdatePropietario(propietario);
        propietarioDAO.save(propietario);
        System.out.println("Propietario guardado en BD.");
    }

    private static void mostrarTodosLosVehiculos() {
        List<Vehiculo> vehiculos = vehiculoDAO.getAll();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            System.out.println("Lista de vehículos:");
            System.out.println("");
            for (Vehiculo vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        }
    }

    private static void buscarVehiculoPorMatricula() {
        System.out.print("Matrícula: ");
        String matricula = input.nextLine();

        Vehiculo vehiculo = vehiculoDAO.getByMatricula(matricula);
        System.out.println("");
        if (vehiculo != null) {
            System.out.println(vehiculo);
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    private static void registrarMantenimiento() {
        List<Vehiculo> vehiculos = vehiculoDAO.getAll();

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println((i + 1) + ". " + vehiculos.get(i).getMatricula());
        }

        Vehiculo vehiculo = null;
        while (vehiculo == null) {
            System.out.print("ID del vehículo (no pongas nada para salir): ");
            String MatriculaVehiculo = input.nextLine();
            if (MatriculaVehiculo.isEmpty()) {
                return;
                // break;
            }
            vehiculo = vehiculoDAO.getByMatricula(MatriculaVehiculo);
            if (vehiculo == null) {
                System.out.println("El vehículo con matrícula '" + MatriculaVehiculo + "' no existe.");
            }
        }

        System.out.print("Fecha (dd/MM/yyyy): ");
        String fechaStr = input.nextLine();
        System.out.print("Descripción: ");
        String descripcion = input.nextLine();
        System.out.print("Coste: ");
        BigDecimal coste = input.nextBigDecimal();
        input.nextLine(); // Limpiar buffer

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = dateFormat.parse(fechaStr);

            HistorialMantenimiento mantenimiento = new HistorialMantenimiento();
            mantenimiento.setVehiculo(vehiculo);
            mantenimiento.setFecha(fecha);
            mantenimiento.setDescripcion(descripcion);
            mantenimiento.setCoste(coste);

            // xmlGenerator.addOrUpdateHistorialMantenimiento(mantenimiento);
            historialMantenimientoDAO.save(mantenimiento);
            System.out.println("Mantenimiento registrado en BD.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void listarHistorialMantenimientos() {
        List<Vehiculo> vehiculos = vehiculoDAO.getAll();

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println((i + 1) + ". " + vehiculos.get(i).getMatricula());
        }

        System.out.print("Matrícula del vehículo: ");
        String matricula = input.nextLine();

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

        List<Vehiculo> vehiculos = vehiculoDAO.getAll();

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println((i + 1) + ". " + vehiculos.get(i).getMatricula());
        }

        System.out.print("Matrícula del vehículo: ");
        String matricula = input.nextLine();
        System.out.print("Nuevo precio: ");
        BigDecimal nuevoPrecio = input.nextBigDecimal();
        input.nextLine(); // Limpiar buffer

        Vehiculo vehiculo = vehiculoDAO.getByMatricula(matricula);
        if (vehiculo != null) {
            vehiculo.setPrecio(nuevoPrecio);
            vehiculoDAO.update(vehiculo);
            // xmlGenerator.addOrUpdateVehiculo(vehiculo);
            System.out.println("Precio actualizado.");
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    private static void eliminarVehiculo() {

        List<Vehiculo> vehiculos = vehiculoDAO.getAll();

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("Vehículos disponibles:");
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println((i + 1) + ". " + vehiculos.get(i).getMatricula());
        }

        System.out.print("Matrícula del vehículo: ");
        String matricula = input.nextLine();

        Vehiculo vehiculo = vehiculoDAO.getByMatricula(matricula);
        if (vehiculo != null) {
            // Hibernate.initialize(vehiculo.getHistorialesMantenimiento());
            if (vehiculo.getHistorialesMantenimiento() != null && !vehiculo.getHistorialesMantenimiento().isEmpty()) {
                System.out.print("Este vehículo tiene mantenimientos registrados. ¿Desea eliminarlo? (s/n): ");
                char confirmacion = input.nextLine().charAt(0);
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