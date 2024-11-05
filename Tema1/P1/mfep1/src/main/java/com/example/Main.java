package com.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String filepath = "libros.txt";

        // Crear array de libros
        Libro[] libros = new Libro[] {
                new Libro("Naruto", "Masashi Kishimoto", 500, 1234567890, true),
                new Libro("One Piece", "Eiichiro Oda", 1000, 2000890123, false),
                new Libro("Dragon Ball", "Akira Toriyama", 300, 1987654321, true),
                new Libro("Bleach", "Tite Kubo", 200, 2045678901, false),
                new Libro("Horimiya", "HERO", 500, 1678901234, true)
        };

        System.out.println("Libros almacenados actualmente: ");

        System.out.println(" ");
        System.out.println("ISBN, Nombre, Autor, Páginas, +18");
        System.out.println(" ");
        for (Libro libro : libros) {
            System.out.println(libro.toString());

            System.out.println(" ");
        }
        System.out.println("Pulsa ENTER para guardar los libros en un archivo.");
        input.nextLine();
        System.out.println(" ");
        System.out.println(" ");

        // Guardar libros en un archivo
        BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));

        for (Libro libro : libros) {
            bw.write(libro.toString());
            bw.newLine();
        }

        bw.close();
        System.out.println("Libros guardados en un archivo.");

        System.out.println(" ");
        System.out.println("Pulsa ENTER para borrar los libros almacenados y después cargarlos desde un archivo.");
        input.nextLine();
        System.out.println(" ");
        System.out.println("Libros borrados correctamente.");
        System.out.println(" ");
        System.out.println(" ");

        // Borrar libros del array
        libros = new Libro[] {};

        System.out.println("Libros almacenados actualmente: ");
        for (Libro libro : libros) {
            System.out.println(libro.toString());
        }

        System.out.println(" ");
        System.out.println("Pulsa ENTER para cargar los libros desde un archivo.");
        input.nextLine();
        System.out.println(" ");
        System.out.println(" ");
        
        // Leer libros del archivo
        BufferedReader br = new BufferedReader(new FileReader(filepath));

        String line;

        System.out.println("Libros cargados desde un archivo: ");

        System.out.println(" ");
        System.out.println("ISBN, Nombre, Autor, Páginas, +18");
        System.out.println(" ");
        while ((line = br.readLine()) != null) {
            Libro libro = Libro.fromString(line);
            System.out.println(libro.toString());
            System.out.println(" ");
        }

        input.close();
        br.close();
    }
}