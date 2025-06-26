<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="modelo.Usuario" %>
<%@ page session="true" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || !"empleado".equals(usuario.getRol())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<h2>Bienvenido Empleado: <%= usuario.getUsuario() %></h2>
<p>Solo tienes acceso al módulo de facturación</p>
<a href="${pageContext.request.contextPath}/LogoutServlet">Cerrar sesión</a>

