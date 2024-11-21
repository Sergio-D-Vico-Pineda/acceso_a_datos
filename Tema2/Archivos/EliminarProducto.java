
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EliminarProducto {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/repasillo";
        String usuario = "root";
        String contrasena = "root";

        String eliminarSQL = "DELETE FROM Productos WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasena);
             PreparedStatement pstmt = conn.prepareStatement(eliminarSQL)) {

            pstmt.setInt(1, 1);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto eliminado con éxito.");
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }
}
