package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.Usuario;
import util.ConexionBD;

public class UsuarioDAO {

    /**
     * Valida el usuario y contraseña (ya hasheada)
     * @param usuario nombre de usuario
     * @param contrasenaHash contraseña en SHA-256
     * @return Usuario si es válido, null si no
     */
    public Usuario validarUsuario(String usuario, String contrasenaHash) {
        Usuario u = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasenaHash);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setUsuario(rs.getString("usuario"));
                    // No es necesario guardar la contraseña en el objeto
                    u.setRol(rs.getString("rol"));
                }
            }

        } catch (Exception e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
        }

        return u;
    }
}
