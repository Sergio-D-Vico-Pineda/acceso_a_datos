package com.example;

import java.util.ArrayList;

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
        // rellenar con ceros hasta el tamaño de 15 caracteres
        String name = String.format("%-15s", this.name);
        String price = String.format("%-10.2f", this.price);
        String quantity = String.format("%-5d", this.quantity);
        return "Nombre: " + name + " | Precio: " + price + " | Cantidad: " + quantity;
    }

    @Override
    public String toString() {
        return name + ":" + price + ":" + quantity + ";";
    }

    public static ArrayList<Product> fromString(String s) {
        String[] products = s.split(";");
        ArrayList<Product> result = new ArrayList<>();
        for (int i = 0; i < products.length; i++) {
            String[] parts = products[i].split(":", 3);
            result.add(new Product(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[2])));
        }
        /* String part4 = parts[2].split(";")[0];
        String part3 = parts[2].substring(0, parts[2].length() - 1);
        System.out.println("part4: " + part4);
        System.out.println("part3: " + part3); */

        return result;
    }

    public static Product fromStringOld(String s) {
        String[] parts = s.split(":", 3);
        return new Product(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[2]));
    }
}
