package com.example;

import java.io.*;
import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);
    static String mainFilepath = "default.txt";
    static int id = 0;

    public static void main(String[] args) {
        int option = -1;
        String color = "\u001B[31m";

        System.out.println(" ");
        id = CountProducts(mainFilepath);
        /* ChangeFilepath(); */

        do {
            color = new File(mainFilepath).exists() ? "\u001B[32m" : "\u001B[31m";
            for (int i = 0; i < 20; i++) {
                System.out.println(" ");
            }
            System.out.println("Práctica Global 1 - Acceso a datos - Sergio David Vico Pineda");
            System.out.println();
            System.out.println("-------- Menú --------");
            System.out.println("Archivo: " + color + mainFilepath + "\u001B[0m");
            System.out.println("ID: " + id);
            System.out.println();
            System.out.println(" 1 - Crear producto.");
            System.out.println(" 2 - Leer todos los productos. (Acceso secuencial)");
            System.out.println(" 3 - Buscar producto por id. (Acceso aleatorio)");
            System.out.println(" 4 - Actualizar precio de un producto. (Acceso aleatorio)");
            System.out.println(" 5 - Cambiar la ruta del archivo.");
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
                case 3 -> InfoProduct2(); // Buscar producto por id
                case 4 -> UpdatePrice2(); // Actualizar precio de un producto
                case 5 -> ChangeFilepath(); // Cambiar la ruta del archivo
                default -> InvalidOption();
            }

            if (option != 0) {
                System.out.println();
                System.out.print("Presiona ENTER para continuar...");
                input.nextLine();
            }

        } while (option != 0);
    }

    static boolean readFileClean(String filepath) {
        boolean printed = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line = br.readLine();
            br.close();

            Product a = null;
            String[] products = line.split(";");

            for (int i = 0; i < products.length; i++) {
                a = Product.fromString(products[i]);
                if (a != null) {
                    System.out.println(a.showInfo());
                    printed = true;
                }
            }

            System.out.println();

        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31m" + "Error al leer el archivo:" + "\u001B[0m" + " El archivo '" + filepath
                    + "' no existe.");

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return printed;
    }

    static void writeFile(String filepath, boolean appe, String[] text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, appe));

            for (String line : text) {
                bw.write(line);
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

    static public void ChangeFilepath() {

        System.out.println("Por favor, introduce el nombre del archivo a tratar (SIN EL .TXT): ");
        System.out.println("Puedes dejarlo en blanco para usar el archivo 'default.txt'");
        mainFilepath = input.nextLine();
        System.out.println("");
        if (mainFilepath.equals("")) {
            mainFilepath = "default";
        }
        mainFilepath += ".txt";
        File file = new File(mainFilepath);
        if (!file.exists()) {
            System.out.println("El archivo '" + mainFilepath + "' se creará al añadir algun producto.");
        } else {
            System.out.println("El archivo '" + mainFilepath + "' ya existe. " +
                    "Los nuevos productos se anadirán al final.");
        }

        System.out.println("La nueva ruta es: " + mainFilepath);
    }

    static public void CreateProduct() {
        System.out.println("Creando nuevo producto.");
        System.out.println("------------------------------");
        System.out.println("");
        System.out.println("MÁXIMO DE CARACTERES: 15");
        System.out.print("Por favor, introduce el NOMBRE del nuevo producto: ");
        String name = input.nextLine();
        double price = -1;
        int quantity = -1;

        do {
            System.out.println("MÁXIMO DE CARACTERES: 9 con los decimales.");
            System.out.print("Por favor, introduce el PRECIO del nuevo producto: ");
            try {
                price = input.nextDouble();
            } catch (Exception e) {
            }

            if (price < 0.0 || price > 9999999.99) {
                System.out.println("\u001B[31m" + "Precio inválido." + "\u001B[0m");
                System.out.println("");
            }

            input.nextLine();
        } while (price < 0.0 || price > 9999999.99);

        do {
            System.out.println("MÁXIMO DE CARACTERES: 5");
            System.out.print("Por favor, introduce la CANTIDAD del nuevo producto: ");
            try {
                quantity = input.nextInt();
            } catch (Exception e) {
            }

            if (quantity < 0 || quantity > 99999) {
                System.out.println("\u001B[31m" + "Cantidad inválida." + "\u001B[0m");
                System.out.println("");
            }

            input.nextLine();
        } while (quantity < 0 || quantity > 99999);

        writeFile(mainFilepath, true, new String[] { new Product(id++, name, price, quantity).toString() });

        System.out.println("");
        System.out.println("\u001B[32m" + "Producto creado correctamente." + "\u001B[0m");
    }

    static public Product SearchProduct2(int id, String filepath) {

        Product prod = null;
        try {
            RandomAccessFile raf = new RandomAccessFile(filepath, "rw");

            quantity = Product.fromString(products[i]).getName().length(); // consigo la longitud del nombre a imprimir
            byte[] bytes = new byte[quantity]; // declaro el array con la longitud del nombre para imprimir
            raf.seek(pos + id + 2); // le sumo el número del id para saltar los separadores ; entre productos y 2 para quedarme en el inicio del nombre
            raf.read(bytes, 0, quantity); // leo y guardo los bytes

            System.out.println("Posicion: " + pos + " Longitud: " + quantity);
            System.out.println("Nombre del producto: " + new String(bytes));
            raf.close();

        } catch (Exception e) {
            System.out.println("\u001B[31m" + "Producto no encontrado. Id no encontrado." + "\u001B[0m");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    static public void InfoProduct2() {
        System.out.println("Mostrando información de un producto:");
        System.out.println("------------------------------");

        System.out.println("");
        System.out.print("Por favor, introduce el id del producto: ");
        int id = -1;
        try {
            id = input.nextInt();
        } catch (Exception e) {
        }

        input.nextLine();

        System.out.println("");

        SearchProduct2(id, mainFilepath);

        /*        if (product != null) */
        /*            System.out.println(product.showInfo()); */
    }

    static public void ShowProducts() {
        System.out.println("Mostrando todos los productos:");
        System.out.println("------------------------------");
        if (!readFileClean(mainFilepath)) {
            System.out.println("\u001B[31m" + "No hay productos en el sistema." + "\u001B[0m");
            return;
        }
    }

    static public void UpdatePrice2() {
        System.out.println("Actualizando precio de un producto: v2");
        System.out.println("-----------------------------------");
        System.out.println("Por favor, introduce el id del producto: ");
        int id = input.nextInt();
        input.nextLine();
        System.out.println("");
    }

    static public void UpdatePrice() {
        System.out.println("Actualizando precio de un producto:");
        System.out.println("-----------------------------------");

        ArrayList<Product> products = new ArrayList<>();

        if (products.size() == 0) {
            System.out.println("\u001B[31m" + "No hay productos en el sistema." + "\u001B[0m");
            return;
        }

        System.out.println("Hay " + products.size() + " productos en el sistema.");
        System.out.println("");
        System.out.println("Por favor, introduce el id del producto: ");
        int id = input.nextInt();
        input.nextLine();
        System.out.println("");

        Product product = /* SearchProduct(id, products) */ new Product(id, mainFilepath, id, id);

        if (product != null) {
            double newprice = -1;
            System.out.println("Actualizando el precio del siguiente producto: ");
            System.out.println(product.showInfo());
            System.out.println("");
            do {
                System.out.println("Introduce el nuevo precio:");
                try {
                    newprice = input.nextDouble();
                } catch (Exception e) {
                }
                if (newprice < 0) {
                    System.out.println("\u001B[31m" + "Precio no válido." + "\u001B[0m");
                    System.out.println("");
                }
                input.nextLine();
            } while (newprice < 0);

            product.updatePrice(newprice);

            String[] lines = new String[products.size()];

            for (int i = 0; i < products.size(); i++) {
                lines[i] = products.get(i).toString();
            }

            writeFile(mainFilepath, false, lines);

            System.out.println("");
            System.out.println("\u001B[32m" + "Precio del producto actualizado correctamente." + "\u001B[0m");
        }
    }

    static public int CountProducts(String filepath) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line = br.readLine();
            br.close();

            count = line.split(";").length;

        } catch (Exception e) {
            System.out.println("\u001B[31m" + "Error al leer el archivo:" + "\u001B[0m" + " El archivo '" + filepath
                    + "' no existe.");
        }
        return count;
    }

}