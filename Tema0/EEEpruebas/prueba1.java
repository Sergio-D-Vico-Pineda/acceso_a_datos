import java.util.ArrayList;

/**
 * practica
 */
public class prueba1 {

    public static void main(String[] args) {
        ArrayList<Integer> grades = new ArrayList<Integer>();
        grades.add(1);
        grades.add(2);
        grades.add(3);
        grades.add(4);
        grades.add(5);
        grades.add(6);
        String s = grades.toString().substring(1, grades.toString().length() - 1);
        System.out.println("Grades: " + s);
        /*
         * System.out.println();
         * System.out.println("Enter a phrase: ");
         * 
         * Scanner sc = new Scanner(System.in);
         * String cadena = sc.nextLine();
         * sc.close();
         * 
         * System.out.printf("\nIn uppercase: " + cadena.toUpperCase());
         * System.out.printf("\nThe length is: " + cadena.length());
         * System.out.printf("\nFirst and last characters are: " + cadena.charAt(0) +
         * cadena.charAt(cadena.length() - 1));
         */
    }
}