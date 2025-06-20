package dao;

import modelo.Producto;
import modelo.Proveedor;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Obtener lista de productos con su proveedor
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.*, pr.nombre AS nombre_proveedor, pr.id AS id_proveedor " +
                     "FROM productos p INNER JOIN proveedores pr ON p.id_proveedor = pr.id";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto prod = new Producto();
                prod.setId(rs.getInt("id"));
                prod.setNombre(rs.getString("nombre"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getDouble("precio")); // Usamos double
                prod.setStock(rs.getInt("stock"));

                Proveedor prov = new Proveedor();
                prov.setId(rs.getInt("id_proveedor"));
                prov.setNombre(rs.getString("nombre_proveedor"));
                prod.setProveedor(prov);

                lista.add(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Agregar producto
    public boolean agregar(Producto prod) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, id_proveedor) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prod.getNombre());
            ps.setString(2, prod.getDescripcion());
            ps.setDouble(3, prod.getPrecio()); // Ajustado
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getProveedor().getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar por ID
    public Producto obtenerPorId(int id) {
        String sql = "SELECT p.*, pr.nombre AS nombre_proveedor FROM productos p " +
                     "INNER JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.id = ?";
        Producto prod = null;

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prod = new Producto();
                    prod.setId(rs.getInt("id"));
                    prod.setNombre(rs.getString("nombre"));
                    prod.setDescripcion(rs.getString("descripcion"));
                    prod.setPrecio(rs.getDouble("precio"));
                    prod.setStock(rs.getInt("stock"));

                    Proveedor prov = new Proveedor();
                    prov.setId(rs.getInt("id_proveedor"));
                    prov.setNombre(rs.getString("nombre_proveedor"));
                    prod.setProveedor(prov);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prod;
    }

    // Actualizar producto
    public boolean actualizar(Producto prod) {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, precio=?, stock=?, id_proveedor=? WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prod.getNombre());
            ps.setString(2, prod.getDescripcion());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getProveedor().getId());
            ps.setInt(6, prod.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar producto
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
