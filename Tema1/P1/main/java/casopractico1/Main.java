package casopractico1;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String filepath = "ejemplo.txt"; // Para no hardcodear

        // Crear usuarios

        Usuario[] usuarios = new Usuario[] {
                new Usuario("Pepe", 35, "pepe@pe.com", 45),
                new Usuario("David", 33, "pepa@pe.com", 43),
                new Usuario("Pepito", 32, "pepito@pe.com", 42),
                new Usuario("Pepa", 33, "pepa@pe.com", 43),
        };

        // Varias formas de escribir, esta es la mas eficiente
        BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
        for (Usuario usuario : usuarios) {
            bw.write(usuario.toString());
            bw.newLine();
        }
        bw.close();

        /* FileWriter fw = new FileWriter(filepath);
        for (Usuario usuario : usuarios) {
            fw.write(usuario.toString() + "\n");
        }
        fw.close(); */

        /* for (int i = 0; i < usuarios.length; i++) {
            filepath = "ejemplo" + (i + 1) + ".txt";
            FileWriter fw = new FileWriter(filepath);
            fw.write(usuarios[i].toString() + "\n");
            fw.close();
        } */

        // Lectura

        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String line;
        while ((line = br.readLine()) != null) {
            Usuario user = Usuario.fromString(line);
            System.out.println(user.toString());
            System.out.println("Nombre: " + user.nombre);
            System.out.println("Edad: " + user.edad);
            System.out.println("Correo: " + user.correo);
            System.out.println("Talla de pie: " + user.talladepie);
        }
        br.close();

        /*FileReader fr = new FileReader(filepath);
        StringBuilder sb = new StringBuilder();
        int charc;
        while ((charc = fr.read()) != -1) {
            if (charc == '\n') {
                sb.append((char) charc);
            } else {
                String line = sb.toString();
                Usuario user = Usuario.fromString(line);
                System.out.println(user.toString());
                System.out.println("Nombre: " + user.nombre);
                System.out.println("Edad: " + user.edad);
                System.out.println("Correo: " + user.correo);
                System.out.println("Talla de pie: " + user.talladepie);
                sb.setLength(0);
            }
        }
        fr.close();
        System.out.println(sb.toString());
        */
    }
}