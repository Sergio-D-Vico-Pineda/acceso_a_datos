public class Untitled {
    public static void main(String[] args) {

        Persona sergio = new Persona("Sergio", 20);
        Persona david = new Persona("David", 25);
        Persona pepito = new Persona("Pepito", 30);

        sergio.mostrarInfo();
        david.mostrarInfo();
        pepito.mostrarInfo();
        /*
         * int num;
         * 
         * do {
         * System.out.println("Dime un número");
         * num = Scanner.nextInt(); //
         * if (num > 0) {
         * System.out.println("Número positivo");
         * } else if (num < 0) {
         * System.out.println("Número negativo");
         * } else {
         * System.out.println("Número cero");
         * }
         * } while (num != 0);
         */
    }
}

/*
 * INT EDAD = 20;
 * STRING NOMBRE = "SERGIO";
 * CHAR GENERO = 'M';
 * BOOLEAN ESTUDIANTE = true;
 * 
 * System.out.println(EDAD);
 * System.out.println(NOMBRE);
 * System.out.println(GENERO);
 * System.out.println(ESTUDIANTE);
 */

// Crear un programa que pida un número al usuario y le diga si es positivo,
// negativo o cero. Luego, que lo repita hasta que el usuario ingrese un 0.
