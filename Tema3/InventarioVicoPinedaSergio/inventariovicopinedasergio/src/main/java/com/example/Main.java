package com.example;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("1. Añadir un producto");
            System.out.println("2. Listar todos los productos");
            System.out.println("3. Actualizar el stock de un producto");
            System.out.println("4. Eliminar un producto");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            try {
                opcion = input.nextInt();
            } catch (Exception e) {
                opcion = -1;
            }
            input.nextLine();
            switch (opcion) {
                case 1 -> anadirProducto();
                case 2 -> listarProductos();
                case 3 -> actualizarProducto();
                case 4 -> eliminarProducto();
                case 5 -> salir();
                default -> System.out.println("Opción incorrecta");
            }
            if (opcion != 5) {
                System.out.println("");
                System.out.print("Presiona ENTER para continuar...");
                System.out.println("");
                input.nextLine();
            }
        } while (opcion != 5);
    }

    public static void salir() {
        System.out.println("Bye");
        HibernateUtil.shutdown();
    }

    public static void anadirProducto() {
        System.out.print("Nombre: ");
        String nombre = input.next();
        System.out.print("Precio: ");
        double precio = input.nextDouble();
        System.out.print("Stock: ");
        int stock = input.nextInt();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Producto producto = new Producto(nombre, precio, stock);
        session.persist(producto);

        transaction.commit();
        session.close();
    }

    public static void listarProductos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Producto> productos = session.createQuery("from Producto",
                Producto.class).list();

        productos.forEach(System.out::println);

        session.close();
    }

    public static void actualizarProducto() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();

        System.out.print("ID del producto: ");
        int id = input.nextInt();
        Producto p = session.get(Producto.class, id);
        if (p != null) {
            System.out.print("Nuevo stock: ");
            int nuevoStock = input.nextInt();
            p.setStock(nuevoStock);
            session.merge(p);
            trans.commit();
        } else {
            System.out.println("No existe el producto.");
        }
        session.close();
    }

    public static void eliminarProducto() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trans = session.beginTransaction();

        System.out.print("ID del producto: ");
        int id = input.nextInt();
        Producto pEliminar = session.get(Producto.class, id);
        if (pEliminar != null) {
            session.remove(pEliminar);
            trans.commit();
        } else {
            System.out.println("No existe el producto");
        }
        session.close();
    }
}