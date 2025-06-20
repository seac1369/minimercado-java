package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("contrasena");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario u = usuarioDAO.validarUsuario(usuario, contrasena);

        if (u != null) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", u);

            // Redirección según el rol
            if ("admin".equals(u.getRol())) {
                response.sendRedirect("jsp/admin.jsp");
            } else if ("empleado".equals(u.getRol())) {
                response.sendRedirect("jsp/empleado.jsp");
            } else {
                // Rol desconocido
                response.sendRedirect("jsp/error.jsp");
            }

        } else {
            // Credenciales incorrectas
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }
}

