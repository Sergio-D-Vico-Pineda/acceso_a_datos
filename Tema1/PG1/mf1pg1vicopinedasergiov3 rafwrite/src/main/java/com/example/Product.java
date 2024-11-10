package com.example;

public class Product {
    String name;
    double price;
    int quantity;
    int id;
    
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public Product(int id, String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.quantity = amount;
        this.id = id;
    }

    public void updatePrice(double nuevoPrecio) {
        this.price = nuevoPrecio;
    }

    public String showInfo() {
        // rellenar con ceros hasta el tamaño de 15 caracteres
        String id = String.format("%-3d", this.id);
        String name = String.format("%-15s", this.name);
        String price = String.format("%-10.2f", this.price);
        String quantity = String.format("%-5d", this.quantity);
        return id + "-| Nombre: " + name + " | Precio: " + price + " | Cantidad: " + quantity;
    }
    
    @Override
    public String toString() {
        String id = String.format("%-3d", this.id);
        String name = String.format("%-15s", this.name);
        String price = String.format("%-10.2f", this.price);
        String quantity = String.format("%-5d", this.quantity);
        return id + ":" + name + ":" + price + ":" + quantity + ";";
    }

    public static Product fromString(String line) { // 1:mouse:11.0:2
        String[] parts = line.split(":", 4);
        return new Product(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]),
                Integer.parseInt(parts[3]));
    }
}
