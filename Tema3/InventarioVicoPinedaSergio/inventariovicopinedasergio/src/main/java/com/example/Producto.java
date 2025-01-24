package com.example;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private double precio;

    @Column(name = "stock")
    private int stock;

    public Producto() {
    }

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
