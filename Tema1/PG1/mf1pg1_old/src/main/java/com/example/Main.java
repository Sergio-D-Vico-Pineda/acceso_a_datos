package com.example;

import java.io.*;
import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    static ArrayList<Product> products = new ArrayList<>();

    public static void main(String[] args) {

        products.add(new Product("Laptop", 1000.0, 10));
        products.add(new Product("Smartphone", 500.0, 20));
        products.add(new Product("Tablet", 300.0, 15));

        int option = -1;

        // pruebas
        /* ActualizarPrecio();
        System.out.println("bra");
        input.nextLine(); */
        // pruebas

        do {
            for (int i = 0; i < 20; i++) {
                System.out.println(" ");
            }
            System.out.println("Práctica Global 1 - Acceso a datos - Sergio David Vico Pineda");

            System.out.println();
            System.out.println("-------- Menú --------");
            System.out.println();
            System.out.println(" 1 - Crear producto.");
            System.out.println(" 2 - Leer todos los productos. (Acceso secuencial)");
            System.out.println(" 3 - Buscar producto por id.");
            System.out.println(" 4 - Actualizar precio de un producto.");
            System.out.println(" 5 - Guardar datos en archivo.");
            System.out.println();
            System.out.println(" 0 - Salir del programa.");
            System.out.println();
            System.out.print("Por favor, elige una opción: ");

            try {
                option = input.nextInt();
            } catch (Exception e) {
                option = -1;
                input.next();
            }

            System.out.println();
            input.nextLine();

            switch (option) {
                case 0 -> ExitProgram();
                case 1 -> CreateProduct(); // Crear producto
                case 2 -> ShowProducts(); // Leer todos los productos
                case 3 -> InfoProduct(); // Buscar producto por id
                case 4 -> UpdatePrice(); // Actualizar precio de un producto
                case 5 -> GuardarDatos(); // Guardar datos en archivo
                default -> InvalidOption();
            }

            if (option != 0) {
                System.out.println();
                System.out.print("Presiona ENTER para continuar...");
                input.nextLine();
            }

        } while (option != 0);
    }

    static void leerArchivo(String filepath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    static void escribirArchivo(String filepath, boolean appe, String[] texto) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, appe));

            for (String line : texto) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    static public void InvalidOption() {
        System.out.println("\u001B[31m" + "Opción inválida." + "\u001B[0m");
    }

    static public void ExitProgram() {
        System.out.println("Programa finalizado. Bye.");
    }

    static public Product SearchProduct(int id) {
        try {
            return products.get(id - 1);
        } catch (Exception e) {
            System.out.println("\u001B[31m" + "Producto no encontrado. Id no encontrado." + "\u001B[0m");
            return null;
        }
    }

    static public void CreateProduct() {
        System.out.println("Creando nuevo producto.");
        System.out.println("------------------------------");
        System.out.print("Por favor, introduce el NOMBRE del nuevo producto: ");
        String name = input.nextLine();
        double price = -1;

        do {
            System.out.print("Por favor, introduce el PRECIO del nuevo producto: ");
            try {
                price = input.nextDouble();
            } catch (Exception e) {
            }

            if (price < 0.0) {
                System.out.println("\u001B[31m" + "Precio inválido." + "\u001B[0m");
                System.out.println("");
            }

            input.nextLine();
        } while (price < 0.0);

        int quantity = -1;

        do {
            System.out.print("Por favor, introduce la CANTIDAD del nuevo producto: ");
            try {
                quantity = input.nextInt();
            } catch (Exception e) {
            }

            if (quantity < 0) {
                System.out.println("\u001B[31m" + "Cantidad inválida." + "\u001B[0m");
                System.out.println("");
            }

            input.nextLine();
        } while (quantity < 0);

        products.add(new Product(name, price, quantity));

        System.out.println("");
        System.out.println("\u001B[32m" + "Producto creado correctamente." + "\u001B[0m");
    }

    static public void ShowProducts() {
        System.out.println("Mostrando todos los productos:");
        System.out.println("------------------------------");

        char answer;
        // preguntar si leer desde un archivo o desde la memoria
        do {
            System.out.println("¿Quieres leer un archivo?");
            System.out.println("Si(s)/No(n)");
            answer = input.nextLine().charAt(0);
        } while (answer != 's' && answer != 'n');
        if (products.size() == 0) {
            System.out.println("\u001B[31m" + "No hay productos en la memoria." + "\u001B[0m");
            System.out.println("------------------------------");
            

            if (answer == 'n') {
            } else if (answer == 's') {
            }

            // leerArchivo();
        }

        for (Product product : products) {
            System.out.println(product.mostrarInfo());
        }
    }

    static public void InfoProduct() {
        System.out.println("Mostrando información de un producto:");
        System.out.println("------------------------------");

        if (products.size() == 0) {
            System.out.println("\u001B[31m" + "No hay productos en el sistema." + "\u001B[0m");
            return;
        }

        System.out.println("Hay " + products.size() + " productos en el sistema.");
        System.out.println("");
        System.out.print("Por favor, introduce el id del producto: ");
        int id = -1;
        try {
            id = input.nextInt();
        } catch (Exception e) {
        }

        input.nextLine();

        System.out.println("");

        Product product = SearchProduct(id);

        if (product != null)
            System.out.println(product.mostrarInfo());
    }

    static public void UpdatePrice() {
        System.out.println("Actualizando precio de un producto:");
        System.out.println("------------------------------");

        if (products.size() == 0) {
            System.out.println("\u001B[31m" + "No hay productos en el sistema." + "\u001B[0m");
            return;
        }

        System.out.println("Por favor, introduce el id del producto: ");
        int id = input.nextInt();
        input.nextLine();
        System.out.println("");

        Product producto = SearchProduct(id);

        if (producto != null) {
            double nuevoprecio = -1;
            do {
                System.out.println("Introduce el nuevo precio:");
                try {
                    nuevoprecio = input.nextDouble();
                } catch (Exception e) {
                }
                if (nuevoprecio < 0) {
                    System.out.println("\u001B[31m" + "Precio no válido." + "\u001B[0m");
                    System.out.println("");
                }
                input.nextLine();
            } while (nuevoprecio < 0);

            producto.actualizarPrecio(nuevoprecio);
            System.out.println("Precio del producto actualizado correctamente.");
        }
    }

    static public void GuardarDatos() {
        System.out.println("Guardando datos en archivo.");
        System.out.println("------------------------------");

        if (products.size() == 0) {
            System.out.println("\u001B[31m" + "No hay productos para guardar." + "\u001B[0m");
            return;
        }

        System.out.println("Por favor, introduce el nombre del archivo (SIN EL .TXT): ");
        String filepath = input.nextLine();

        File file = new File(filepath + ".txt");
        String[] text = new String[products.size()];

        for (int i = 0; i < products.size(); i++) {
            text[i] = products.get(i).toString();
        }

        char answer = 's';
        if (file.exists()) {
            do {
                System.out.println("¿Quieres sobrescribir el archivo?");
                System.out.println("Si(s)/No(n)");
                answer = input.nextLine().charAt(0);
            } while (answer != 's' && answer != 'n');

            if (answer == 'n') {
                System.out.println("Creando archivo nuevo...");
                filepath = filepath + "_new";
                escribirArchivo(filepath + ".txt", false, text);
            } else if (answer == 's') {
                System.out.println("Sobreescribiendo archivo...");
            }

        }
        if (answer == 's')
            escribirArchivo(filepath + ".txt", false, text);

        System.out.println("");
        System.out.println("Nombre del archivo: " + filepath + ".txt");
        for (String line : text) {
            System.out.println(line);
        }

        // escribirArchivo(filepath, false, text);
    }

}