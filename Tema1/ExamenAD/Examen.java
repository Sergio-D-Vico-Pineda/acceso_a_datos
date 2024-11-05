import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Examen {

    // Estas variables pueden estar dentro del main pero las puse fuera porque al
    // principio tenia pensado hacerlo en modulos.
    static int opcion;
    static Scanner input = new Scanner(System.in);
    static ArrayList<Producto> productos = new ArrayList<>();

    public static void main(String[] args) {

        // Productos por defecto para probar cosas más rápido

        productos.add(new Producto("Libro", 10.0, 5));
        productos.add(new Producto("Revista", 5.0, 10));
        productos.add(new Producto("CD", 15.0, 3));

        do {
            // pedir opcion
            System.out.println("Elija una de las siguientes opciones: ");
            System.out.println("1.- Registrar producto");
            System.out.println("2.- Actualizar stock de un producto");
            System.out.println("3.- Mostrar todos los productos");
            System.out.println("4.- Buscar producto por nombre");
            System.out.println("5.- Mostrar productos con valor de inventarios mayor a");
            System.out.println("6.- Porcentaje aplicable a todos los productos");
            System.out.println("7.- Salir del programa");
            System.out.println("Escribe la opcion: ");

            try {
                // Se pide la entrada del usuario
                opcion = input.nextInt();
            } catch (Exception e) {
                // Si no es un número se pone a -1 para dar error y repetir el bucle
                opcion = -1;
            }
            // Estos nextLine son para evitar que se salte el siguiente nextLine o nextInt
            input.nextLine();
            switch (opcion) {
                case 1:

                    /*
                     * Pedir nombre
                     * Pedir precio
                     * Pedir cantidad inicial del producto
                     * Añadir el nuevo producto a un arrayList
                     */
                    double precio = -1;
                    int cantidadStock = -1;

                    System.out.println("Nombre del producto: ");
                    String name = input.nextLine();
                    do {
                        try {
                            System.out.println("Precio del producto: ");
                            precio = input.nextDouble();
                        } catch (Exception e) {
                            System.out.println(" ");
                            System.err.println("\u001B[31m" + "Precio inválido." + "\u001B[0m");
                            System.out.println(" ");
                        }
                        input.nextLine(); // Para evitar que se salte el siguiente nextInt o nextLine
                    } while (precio <= 0);

                    do {
                        try {
                            System.out.println("Cantidad de stock del producto: ");
                            cantidadStock = input.nextInt();
                        } catch (Exception e) {

                            System.out.println(" ");
                            System.err.println("\u001B[31m" + "Cantidad de stock inválido." + "\u001B[0m");
                            System.out.println(" ");
                        }
                        input.nextLine(); // Para evitar que se salte el siguiente nextInt o nextLine

                    } while (cantidadStock <= 0);

                    Producto newProducto = new Producto(name, precio, cantidadStock);
                    productos.add(newProducto);

                    break;
                case 2:
                    /*
                     * Preguntar por el nombre del producto cuya cantidad se desea cambiar
                     * Preguntar si se quiere incrementar o decrementar la cantidad de producto
                     * Si no se puede realizar el decremento mostrará un mensaje por pantalla que
                     * diga que no se puede realizar el decremento
                     * Si no se elige 1 o 0 para elegir decremento o incremento deberá volver a
                     * pedir que elija
                     */

                    if (productos.size() == 0) {
                        System.err.println("\u001B[31m" + "No hay productos." + "\u001B[0m");
                        break;
                    }

                    boolean found = false;
                    System.out.println("Nombre del producto: ");
                    String nameProducto = input.nextLine();

                    for (Producto product : productos) {
                        if (product.getNombre().equals(nameProducto)) {
                            int incremento = -1;
                            int cantidad = -1;
                            do {
                                System.out.println(" ");
                                System.out.println("¿Incrementar o decrementar?");
                                System.out.println("0.- Incrementar");
                                System.out.println("1.- Decrementar");
                                System.out.println("Escribe la opcion: ");
                                try {
                                    incremento = input.nextInt();
                                } catch (Exception e) {
                                    // Si es inválida se pone a -1 para dar error y repetir el bucle
                                    incremento = -1;
                                    System.err.println("\u001B[31m" + "Opcion inválida." + "\u001B[0m");
                                }
                                input.nextLine(); // Para evitar que se salte el siguiente nextInt o nextLine
                            } while (incremento != 0 && incremento != 1);

                            // Determino si va a sumar o a restar
                            boolean sumar = incremento == 0 ? true : false;

                            if (sumar) {
                                System.out.println("Se va a incrementar");
                            } else {
                                System.out.println("Se va a decrementar");
                            }
                            System.out.println(" ");
                            do {
                                System.out.println("¿Cuantas unidades?");
                                try {
                                    cantidad = input.nextInt();
                                } catch (Exception e) {
                                    // Si es inválida se pone a -1 para dar error y repetir el bucle
                                    cantidad = -1;
                                    System.err.println("\u001B[31m" + "Opción inválida." + "\u001B[0m");
                                }
                                input.nextLine(); // Para evitar que se salte el siguiente nextInt o nextLine
                            } while (cantidad <= 0);

                            if (product.modCantidadStock(sumar, cantidad)) {
                                System.out.println("Se ha modificado el stock.");
                            } else {
                                System.out.println("No se ha podido modificar el stock.");
                            }
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println(" ");
                        System.err.println("\u001B[31m" + "El producto no se ha encontrado." + "\u001B[0m");
                        System.out.println(" ");
                    }

                    break;

                case 3:
                    /* Muestra la información de todos los productos */

                    if (productos.size() == 0) {
                        System.err.println("\u001B[31m" + "No hay productos." + "\u001B[0m");
                        break;
                    }

                    for (Producto product : productos) {
                        product.mostrarInfo();
                        System.out.println(" ");
                    }
                    break;

                case 4:
                    /* Preguntar que producto quiere el usuario que se muestre y mostrar info */

                    if (productos.size() == 0) {
                        System.err.println("\u001B[31m" + "No hay productos." + "\u001B[0m");
                        break;
                    }

                    found = false;
                    System.out.println("Nombre del producto: ");
                    String nameProduct = input.nextLine();

                    for (Producto product : productos) {
                        if (product.getNombre().equals(nameProduct)) {
                            product.mostrarInfo();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println(" ");
                        System.err.println("\u001B[31m" + "El producto no se ha encontrado." + "\u001B[0m");
                        System.out.println(" ");
                    }

                    break;

                case 5:
                    /* Mostrar los productos con valor de inventario mayor a */

                    if (productos.size() == 0) {
                        System.err.println("\u001B[31m" + "No hay productos." + "\u001B[0m");
                        break;
                    }

                    double minPrice = -1;
                    boolean error = false; // Esta variable esta para que no salte los dos errores cuando pones algo que
                                           // no sea un numero
                    boolean thereAreProducts = false;

                    do {
                        System.out.println("Introduce el valor de inventario mínimo: ");
                        try {
                            minPrice = input.nextDouble();
                        } catch (InputMismatchException e) {
                            System.err.println("\u001B[31m" + "Valor de inventario inválida." + "\u001B[0m");
                            error = true;
                        }
                        input.nextLine(); // Para evitar que se salte el siguiente nextInt o nextLine

                        if (minPrice < 0 && error == false) {
                            System.err
                                    .println("\u001B[31m" + "El valor de inventario no puede ser negativo."
                                            + "\u001B[0m");
                        }
                        error = false;
                    } while (minPrice < 0);

                    System.out.println("");

                    for (Producto producto : productos) {
                        if (producto.getPrecioTotal() > minPrice) {
                            producto.mostrarInfo();
                            System.out.println(" ");
                            thereAreProducts = true;
                        }
                    }

                    if (!thereAreProducts) {
                        System.err
                                .println("\u001B[31m" + "No hay productos con valor de inventario superior al indicado."
                                        + "\u001B[0m");
                    }

                    break;

                case 6:
                    /* Pide un porcentaje y aplícalo */

                    if (productos.size() == 0) {
                        System.err.println("\u001B[31m" + "No hay productos." + "\u001B[0m");
                        break;
                    }

                    double porcentaje = 0;
                    boolean ok = false;

                    // Al poder poner un porcentaje negativo hay que cambiar la condición para salir
                    // del bucle por eso el ok
                    do {
                        try {
                            System.out.println("Porcentaje a aplicar a todos los productos: ");
                            porcentaje = input.nextDouble();
                            ok = true;
                        } catch (Exception e) {
                            System.out.println(" ");
                            System.err.println("\u001B[31m" + "Porcentaje inválido." + "\u001B[0m");
                            System.out.println(" ");
                        }
                        input.nextLine(); // Para evitar que se salte el siguiente nextInt o nextLine
                    } while (!ok);

                    for (Producto producto : productos) {
                        producto.actualizarPrecio(porcentaje);
                    }

                    System.out.println("Precios actualizados");

                    break;

                case 7:
                    /* Imprime hasta luego */
                    System.err.println("Programa finalizado. Hasta luego 👋.");
                    break;

                default:
                    /* Mensaje error y repite */
                    System.out.println("\u001B[31m" + "Opción inválida." + "\u001B[0m");
                    break;

            }

            if (opcion != 7) {
                System.out.println();
                System.out.print("Presiona ENTER para continuar...");
                System.out.println();
                input.nextLine(); // Para evitar que se salte el siguiente nextInt o nextLine
            }
        } while (opcion != 7);

    }
}
