package dao;

import modelo.Proveedor;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    // ✅ Método renombrado a "listar"
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setContacto(rs.getString("contacto"));
                p.setDireccion(rs.getString("direccion"));

                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Proveedor obtenerProveedorPorId(int id) {
        Proveedor p = null;
        String sql = "SELECT * FROM proveedores WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Proveedor();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setContacto(rs.getString("contacto"));
                    p.setDireccion(rs.getString("direccion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public boolean agregarProveedor(Proveedor p) {
        String sql = "INSERT INTO proveedores(nombre, contacto, direccion) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getDireccion());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarProveedor(Proveedor p) {
        String sql = "UPDATE proveedores SET nombre = ?, contacto = ?, direccion = ? WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getDireccion());
            ps.setInt(4, p.getId());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM proveedores WHERE id = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

