import java.util.Scanner;

/**
 * practica
 */
public class practica {

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Enter a phrase: ");

        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();
        sc.close();

        System.out.printf("\nIn uppercase: " + cadena.toUpperCase());
        System.out.printf("\nThe length is: " + cadena.length());
        System.out.printf("\nFirst and last characters are: " + cadena.charAt(0) + cadena.charAt(cadena.length() - 1));
    }
}