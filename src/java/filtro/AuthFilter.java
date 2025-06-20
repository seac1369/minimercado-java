package filtro;

import modelo.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Filtra las URLs de admin y empleado
@WebFilter(urlPatterns = {"/jsp/admin.jsp", "/jsp/empleado.jsp"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        String path = req.getServletPath();

        if (usuario == null) {
            // No hay sesión activa, redirigir a login
            res.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // Control simple por rol y página
        if (path.equals("/jsp/admin.jsp") && !"admin".equals(usuario.getRol())) {
            res.sendRedirect(req.getContextPath() + "/jsp/error.jsp");
            return;
        }

        if (path.equals("/jsp/empleado.jsp") && !"empleado".equals(usuario.getRol())) {
            res.sendRedirect(req.getContextPath() + "/jsp/error.jsp");
            return;
        }

        // Si pasa las validaciones, continúa la petición
        chain.doFilter(request, response);
    }
}
