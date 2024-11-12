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
        // rellenar con ceros hasta el tamaño de x caracteres
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

    public static Product fromString(String line) { // 0  :mouse          :15,40     :1
        String[] parts = line.split(":", 4);
        int id1 = Integer.parseInt(parts[0].trim());
        double price1 = Double.parseDouble(parts[2].trim().replace(",", "."));
        int quantity1 = Integer.parseInt(parts[3].trim());
        return new Product(id1, parts[1].trim(), price1, quantity1);
    }
}
