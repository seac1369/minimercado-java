package modelo;

import java.sql.Timestamp;
import java.util.List;

public class Factura {
    private int id;
    private Timestamp fecha;
    private double total;
    private List<DetalleFactura> detalles;

    public Factura() {}

    public Factura(int id, Timestamp fecha, double total) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<DetalleFactura> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFactura> detalles) { this.detalles = detalles; }
}
