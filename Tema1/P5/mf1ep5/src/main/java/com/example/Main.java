package com.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        leerArchivoContarLineas("estudiantes.txt");
    }

    static void leerArchivoContarLineas(String filepath) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                count++;
            }
            br.close();
            System.out.println("El archivo tiene " + count + " lineas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}