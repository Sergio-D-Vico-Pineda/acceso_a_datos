package com.example;

public class Product {
    String nombre;
    double precio;
    int cantidad;

    public Product(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void actualizarPrecio(double nuevoPrecio) {
        this.precio = nuevoPrecio;
    }

    public String mostrarInfo() {
        // rellenar con ceros hasta el tamaño de 20 caracteres
        String nombre = String.format("%-15s", this.nombre);
        String precio = String.format("%-10.2f", this.precio);
        String cantidad = String.format("%-5d", this.cantidad);
        return "Nombre: " + nombre + " | Precio: " + precio + " | Cantidad: " + cantidad;
    }

    @Override
    public String toString() {
        return nombre + "|" + precio + "|" + cantidad;
    }

    public static Product fromString(String s) {
        String[] parts = s.split("|");
        return new Product(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[2]));
    }
}
