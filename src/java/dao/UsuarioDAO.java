package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Usuario;
import util.ConexionBD;

public class UsuarioDAO {

    public Usuario validarUsuario(String usuario, String contrasena) {
        Usuario u = null;
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setContrasena(rs.getString("contrasena"));
                    u.setRol(rs.getString("rol"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return u;
    }
}
