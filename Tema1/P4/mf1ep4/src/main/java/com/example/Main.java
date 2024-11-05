package com.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Se le puede cambiar el formato a las imagenes, al menos de png a jpg
            FileInputStream fis = new FileInputStream("horario.png");
            FileOutputStream fos = new FileOutputStream("copia_imagen.jpg");
            int byteLeido;
            while ((byteLeido = fis.read()) != -1) {
                fos.write(byteLeido);
            }
            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe o no se ha encontrado.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Se ha producido un error de E/S.");
            e.printStackTrace();
        }
    }
}