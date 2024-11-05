package com.example;

public class Product {
    String name;
    double price;
    int quantity;

    public Product(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.quantity = amount;
    }

    public void updatePrice(double nuevoPrecio) {
        this.price = nuevoPrecio;
    }

    public String showInfo() {
        // rellenar con ceros hasta el tamaño de 20 caracteres
        String name = String.format("%-15s", this.name);
        String price = String.format("%-10.2f", this.price);
        String quantity = String.format("%-5d", this.quantity);
        return "Nombre: " + name + " | Precio: " + price + " | Cantidad: " + quantity;
    }

    @Override
    public String toString() {
        return name + ":" + price + ":" + quantity;
    }

    public static Product fromString(String s) {
        String[] parts = s.split(":", 3);
        return new Product(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[2]));
    }
}
