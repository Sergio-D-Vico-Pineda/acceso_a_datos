/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example;

/**
 *
 * @author DonScarpy
 */
public class Libro {

    String titulo;
    String autor;
    int paginas;
    long ISBN;
    boolean is18;

    public Libro(String titulo, String autor, int paginas, long ISBN, boolean is18) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.ISBN = ISBN;
        this.is18 = is18;
    }

    @Override
    public String toString() {
        return this.ISBN + ", " + this.titulo + ", " + this.autor + ", " + this.paginas + ", " + this.is18;
    }

    public static Libro fromString(String str) {
        String[] parts = str.split(", ");
        return new Libro(parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[0]),
                Boolean.parseBoolean(parts[4]));
    }
}