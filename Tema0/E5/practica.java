import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * practica
 */
public class practica {

    public static void main(String[] args) {
        System.out.println();
        int num1 = 0, num2 = 0;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter a number: ");
            num1 = sc.nextInt();
            System.out.println("Enter another number: ");
            num2 = sc.nextInt();
            sc.close();
            System.out.printf("The result is %s", num1 / num2);
        } catch (InputMismatchException e) {
            System.out.println("Error: You must enter numbers!");
        } catch (ArithmeticException e) {
            System.out.println("Error: It's impossible to divide by zero!");
        }
        /*
         * Scanner sc = new Scanner(System.in);
         * System.out.println("Enter a number: ");
         * num1 = sc.nextInt();
         * System.out.println("Enter another number: ");
         * num2 = sc.nextInt();
         * sc.close();
         */

        /* try {
            
        } catch (ArithmeticException e) {
            System.out.println("It's impossible to divide by zero!");
        } */
    }
}