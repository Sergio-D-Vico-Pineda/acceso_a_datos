
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertarProducto {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/repasillo";
        String usuario = "root";
        String contrasena = "root";

        String insertarProductoSQL = "INSERT INTO Productos (nombre, precio, cantidad) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena);
             PreparedStatement pstmt = conn.prepareStatement(insertarProductoSQL)) {

            pstmt.setString(1, "Manzanas");
            pstmt.setBigDecimal(2, new java.math.BigDecimal("1.99"));
            pstmt.setInt(3, 100);
            pstmt.executeUpdate();

            System.out.println("Producto insertado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }
}
