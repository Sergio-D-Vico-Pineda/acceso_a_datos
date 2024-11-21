
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeerProductos {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/repasillo";
        String usuario = "root";
        String contrasena = "root";

        String consultaSQL = "SELECT * FROM Productos";

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(consultaSQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");

                System.out.println("ID: " + id + ", Nombre: " + nombre +
                                   ", Precio: " + precio + ", Cantidad: " + cantidad);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer productos: " + e.getMessage());
        }
    }
}
