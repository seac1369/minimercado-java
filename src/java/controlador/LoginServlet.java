package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.Seguridad; // Asegúrate de tener una clase que haga hashing

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

        if (usuario == null || contrasena == null || usuario.isEmpty() || contrasena.isEmpty()) {
            request.setAttribute("error", "Debe completar ambos campos.");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            return;
        }

        // Aplica hash si las contraseñas están hasheadas en BD
        String hash = Seguridad.hash(contrasena);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario u = usuarioDAO.validarUsuario(usuario, hash);

        if (u != null) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", u);
            sesion.setMaxInactiveInterval(30 * 60); // 30 minutos

            switch (u.getRol()) {
                case "admin":
                    response.sendRedirect("jsp/admin.jsp");
                    break;
                case "empleado":
                    response.sendRedirect("jsp/empleado.jsp");
                    break;
                default:
                    response.sendRedirect("jsp/error.jsp");
            }

        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }
}
