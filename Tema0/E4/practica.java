import java.util.ArrayList;
import java.util.Collections;

public class practica {
   public static void main(String[] args) {
      ArrayList<String> nombres = new ArrayList<>();
      ArrayList<int> edades = new ArrayList<>();

      nombres.add("Ana");
      nombres.add("Luis");
      nombres.add("Pedro");

      /*
       * for (int i = nombres.size() - 1; i >= 0; i--) {
       * System.out.printf("\n" + nombres.get(i));
       * }
       */

      for (String nombre : nombres.reversed()) {
         System.out.printf("\n" + nombre);
      }

   }
}