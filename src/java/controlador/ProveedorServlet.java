package controlador;

import dao.ProveedorDAO;
import modelo.Proveedor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProveedorServlet")
public class ProveedorServlet extends HttpServlet {
    ProveedorDAO dao = new ProveedorDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "listar":
                List<Proveedor> lista = dao.listar();
                request.setAttribute("listaProveedores", lista);
                request.getRequestDispatcher("jsp/proveedores/listar.jsp").forward(request, response);
                break;

            case "nuevo":
                request.getRequestDispatcher("jsp/proveedores/nuevo.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Proveedor proveedor = dao.obtenerProveedorPorId(idEditar);
                request.setAttribute("proveedor", proveedor);
                request.getRequestDispatcher("jsp/proveedores/editar.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.eliminarProveedor(idEliminar);
                response.sendRedirect("ProveedorServlet?action=listar");
                break;

            default:
                response.sendRedirect("ProveedorServlet?action=listar");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("guardar".equals(action)) {
            Proveedor p = new Proveedor();
            p.setNombre(request.getParameter("nombre"));
            p.setContacto(request.getParameter("contacto"));
            p.setDireccion(request.getParameter("direccion"));
            dao.agregarProveedor(p);
        } else if ("actualizar".equals(action)) {
            Proveedor p = new Proveedor();
            p.setId(Integer.parseInt(request.getParameter("id")));
            p.setNombre(request.getParameter("nombre"));
            p.setContacto(request.getParameter("contacto"));
            p.setDireccion(request.getParameter("direccion"));
            dao.actualizarProveedor(p);
        }

        response.sendRedirect("ProveedorServlet?action=listar");
    }
}
