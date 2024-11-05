import java.io.*;

public class Prueba {
    public static void main(String[] args) throws IOException {
        BufferedReader brLector = new BufferedReader(new FileReader("ejemplo.txt"));

 
        String linea;
        System.out.println(" ");
        while ((linea = brLector.readLine()) != null) {
            System.out.println(linea);
        }

        brLector.close();
        /*
         * FileWriter fw = new FileWriter("ejemplo.txt");
         * 
         * fw.write("Hola\nMundo");
         * 
         * fw.close();
         */
        /*
         * File archivo = new File("ejemplo.txt");
         * // Comprueba si el archivo existe si no lo crea
         * if (archivo.createNewFile()) {
         * System.out.println("El archivo se ha creado.");
         * } else {
         * System.out.println("El archivo ya existe.");
         * }
         */
        /*
         * if (archivo.exists()) {
         * System.out.println("El archivo existe.");
         * } else {
         * archivo.createNewFile();
         * System.out.println("El archivo se ha creado.");
         * }
         */
    }
}
