package util;

import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            System.out.println("✅ Conexión exitosa a la base de datos");
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
    }
}
