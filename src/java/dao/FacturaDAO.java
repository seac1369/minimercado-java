package dao;

import modelo.*;

import java.sql.*;
import util.ConexionBD;
import java.util.List;
import java.util.ArrayList;

public class FacturaDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public int registrarFactura(Factura factura) {
        int idGenerado = -1;
        String sqlFactura = "INSERT INTO facturas (total) VALUES (?)";
        try {
            con = ConexionBD.obtenerConexion();
            ps = con.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, factura.getTotal());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
                for (DetalleFactura detalle : factura.getDetalles()) {
                    registrarDetalle(idGenerado, detalle);
                    descontarStock(detalle.getIdProducto(), detalle.getCantidad());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al registrar factura: " + e);
        }
        return idGenerado;
    }

    private void registrarDetalle(int idFactura, DetalleFactura detalle) throws SQLException {
        String sqlDetalle = "INSERT INTO detalle_factura (id_factura, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        ps = con.prepareStatement(sqlDetalle);
        ps.setInt(1, idFactura);
        ps.setInt(2, detalle.getIdProducto());
        ps.setInt(3, detalle.getCantidad());
        ps.setDouble(4, detalle.getPrecioUnitario());
        ps.executeUpdate();
    }

    private void descontarStock(int idProducto, int cantidadVendida) throws SQLException {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, cantidadVendida);
        ps.setInt(2, idProducto);
        ps.executeUpdate();
    }

    public List<Factura> listarFacturas() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas ORDER BY fecha DESC";

        try {
            con = ConexionBD.obtenerConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Factura f = new Factura();
                f.setId(rs.getInt("id"));
                f.setFecha(rs.getTimestamp("fecha"));
                f.setTotal(rs.getDouble("total"));
                lista.add(f);
            }
        } catch (Exception e) {
            System.out.println("Error al listar facturas: " + e.getMessage());
        }
        return lista;
    }

    public List<DetalleFactura> obtenerDetallesPorFactura(int idFactura) {
        List<DetalleFactura> detalles = new ArrayList<>();
        String sql = "SELECT df.*, p.nombre FROM detalle_factura df "
                + "JOIN productos p ON df.id_producto = p.id "
                + "WHERE df.id_factura = ?";

        try {
            con = ConexionBD.obtenerConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idFactura);
            rs = ps.executeQuery();

            while (rs.next()) {
                DetalleFactura df = new DetalleFactura();
                df.setId(rs.getInt("id"));
                df.setIdFactura(rs.getInt("id_factura"));
                df.setIdProducto(rs.getInt("id_producto"));
                df.setCantidad(rs.getInt("cantidad"));
                df.setPrecioUnitario(rs.getDouble("precio_unitario"));

                // Producto embebido (nombre) opcional
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                df.setProducto(p);

                detalles.add(df);
            }

        } catch (Exception e) {
            System.out.println("Error al obtener detalles de factura: " + e.getMessage());
        }
        return detalles;
    }

}
