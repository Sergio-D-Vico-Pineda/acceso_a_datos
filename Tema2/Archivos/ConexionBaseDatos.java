
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/repasillo";
        String usuario = "root";
        String contrasena = "root";

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena)) {
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}
