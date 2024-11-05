package com.example;

import java.io.*;

/* •Crear un archivo llamado alumnos.txt con 5 registros de alumnos.
•Leer el archivo y mostrar su contenido
•Añadir 2 nuevos registros sin sobrescribir los existentes.
•Manejar las posibles excepciones durante la operación.
Entregarlo en un zip llamado MF1EP2ApellidosNombre.zip */

public class Main {
    public static void main(String[] args) {
        String filepath = "alumnos.txt";
        String[] alumnos = new String[] {
                "Mady", "Sergio", "Atteneri", "David", "Paco"
        };

        escribirArchivo(filepath, false, alumnos);

        leerArchivo(filepath);
        System.out.println("-----------------------");

        escribirArchivo(filepath, true, new String[] { "Ahmed", "Pedro" });

        leerArchivo(filepath);
    }

    static void leerArchivo(String filepath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    static void escribirArchivo(String filepath, boolean appe, String[] texto) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, appe));

            for (String line : texto) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}