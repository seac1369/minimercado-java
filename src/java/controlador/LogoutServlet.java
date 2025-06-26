package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalida la sesión actual
        HttpSession sesion = request.getSession(false); // no crea una nueva si no existe
        if (sesion != null) {
            sesion.invalidate();
        }

        // Redirige al login con mensaje opcional
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?logout=1");
    }
}

