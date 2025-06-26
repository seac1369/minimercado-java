package controlador;

import dao.*;
import modelo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/FacturaServlet")
public class FacturaServlet extends HttpServlet {

    FacturaDAO facturaDAO = new FacturaDAO();
    ProductoDAO productoDAO = new ProductoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "nueva";

        if (action.equals("nueva")) {
            List<Producto> productos = productoDAO.listar();
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("jsp/facturas/nueva.jsp").forward(request, response);
        } else if (action.equals("listar")) {
            List<Factura> facturas = facturaDAO.listarFacturas();
            request.setAttribute("facturas", facturas);
            request.getRequestDispatcher("jsp/facturas/listar.jsp").forward(request, response);
        } else if (action.equals("detalle")) {
            int idFactura = Integer.parseInt(request.getParameter("id"));
            List<DetalleFactura> detalles = facturaDAO.obtenerDetallesPorFactura(idFactura);

            // Obtener total desde la factura por si quieres mostrarlo
            Factura factura = new Factura();
            factura.setId(idFactura);
            factura.setDetalles(detalles);

            double total = 0;
            for (DetalleFactura d : detalles) {
                total += d.getCantidad() * d.getPrecioUnitario();
            }
            factura.setTotal(total);

            request.setAttribute("factura", factura);
            request.setAttribute("detalles", detalles);
            request.getRequestDispatcher("jsp/facturas/detalle.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("guardar".equals(request.getParameter("action"))) {
            String[] ids = request.getParameterValues("idProducto");
            String[] cantidades = request.getParameterValues("cantidad");

            List<DetalleFactura> detalles = new ArrayList<>();
            double total = 0;

            for (int i = 0; i < ids.length; i++) {
                int idProd = Integer.parseInt(ids[i]);
                int cant = Integer.parseInt(cantidades[i]);

                Producto p = productoDAO.obtenerPorId(idProd);
                DetalleFactura d = new DetalleFactura();
                d.setIdProducto(idProd);
                d.setCantidad(cant);
                d.setPrecioUnitario(p.getPrecio());
                total += cant * p.getPrecio();
                detalles.add(d);
            }

            Factura f = new Factura();
            f.setTotal(total);
            f.setDetalles(detalles);

            facturaDAO.registrarFactura(f);
            response.sendRedirect("FacturaServlet?action=nueva");
        }
    }
}
