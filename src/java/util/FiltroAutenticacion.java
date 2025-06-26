package util;

import modelo.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Filtro que controla acceso a las vistas protegidas
 */
@WebFilter("/jsp/*") // Aplica a todo dentro de /jsp
public class FiltroAutenticacion implements Filter {

    public void init(FilterConfig config) {}

    public void destroy() {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession sesion = req.getSession(false);
        Usuario usuario = (sesion != null) ? (Usuario) sesion.getAttribute("usuario") : null;

        String uri = req.getRequestURI();

        if (usuario == null) {
            // Si no hay usuario en sesión, redirige a login
            res.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            return;
        }

        // Control de roles: acceso solo a páginas permitidas
        if (uri.contains("/admin.jsp") && !"admin".equals(usuario.getRol())) {
            res.sendRedirect(req.getContextPath() + "/jsp/error.jsp");
            return;
        }

        if (uri.contains("/empleado.jsp") && !"empleado".equals(usuario.getRol()) && !"admin".equals(usuario.getRol())) {
            res.sendRedirect(req.getContextPath() + "/jsp/error.jsp");
            return;
        }

        // Todo OK, continuar
        chain.doFilter(request, response);
    }
}
