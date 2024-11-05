package com.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String[] text = { "Manzanas - 0,5 euros", "Pizzas - 1,5 euros", "Aviones - 19 euros", "Ordenadores - 50 euros",
                "Cerveza - 1 euros" };
        String path = "datos.txt";

        escribirArchivo(path, text);

        leerArchivo(path);
    }

    static void escribirArchivo(String filepath, String[] content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            for (String line : content) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
    }
}