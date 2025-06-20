package controlador;

import dao.ProductoDAO;
import dao.ProveedorDAO;
import modelo.Producto;
import modelo.Proveedor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {

    private ProductoDAO productoDAO = new ProductoDAO();
    private ProveedorDAO proveedorDAO = new ProveedorDAO(); // para llenar combobox, etc.

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "listar";

        switch (action) {
            case "listar":
                listar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "editar":
                mostrarEditar(request, response);
                break;
            case "nuevo":
                mostrarFormularioNuevo(request, response);
                break;
            default:
                listar(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("agregar".equals(action)) {
            agregar(request, response);
        } else if ("actualizar".equals(action)) {
            actualizar(request, response);
        } else {
            response.sendRedirect("ProductoServlet?action=listar");
        }
    }

    // Métodos internos
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Producto> lista = productoDAO.listar();
        request.setAttribute("productos", lista);
        request.getRequestDispatcher("jsp/productos.jsp").forward(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productoDAO.eliminar(id);
        response.sendRedirect("ProductoServlet?action=listar");
    }

    private void mostrarEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Producto producto = productoDAO.obtenerPorId(id);
        List<Proveedor> proveedores = proveedorDAO.listar();

        request.setAttribute("producto", producto);
        request.setAttribute("proveedores", proveedores);
        request.getRequestDispatcher("jsp/editarProducto.jsp").forward(request, response);
    }

    // ✅ NUEVO: mostrar formulario de nuevo producto
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Proveedor> proveedores = proveedorDAO.listar();
        request.setAttribute("proveedores", proveedores);
        request.getRequestDispatcher("jsp/nuevoProducto.jsp").forward(request, response);
    }

    private void agregar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Producto p = new Producto();
        p.setNombre(request.getParameter("nombre"));
        p.setDescripcion(request.getParameter("descripcion"));
        p.setPrecio(Double.parseDouble(request.getParameter("precio")));
        p.setStock(Integer.parseInt(request.getParameter("stock")));

        Proveedor proveedor = new Proveedor();
        proveedor.setId(Integer.parseInt(request.getParameter("proveedor")));
        p.setProveedor(proveedor);

        productoDAO.agregar(p);
        response.sendRedirect("ProductoServlet?action=listar");
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Producto p = new Producto();
        p.setId(Integer.parseInt(request.getParameter("id")));
        p.setNombre(request.getParameter("nombre"));
        p.setDescripcion(request.getParameter("descripcion"));
        p.setPrecio(Double.parseDouble(request.getParameter("precio")));
        p.setStock(Integer.parseInt(request.getParameter("stock")));

        Proveedor proveedor = new Proveedor();
        proveedor.setId(Integer.parseInt(request.getParameter("proveedor")));
        p.setProveedor(proveedor);

        productoDAO.actualizar(p);
        response.sendRedirect("ProductoServlet?action=listar");
    }
        

}
