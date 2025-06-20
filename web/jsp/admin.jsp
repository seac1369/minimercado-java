<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="modelo.Usuario" %>
<%@ page session="true" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || !"admin".equals(usuario.getRol())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<h2>Bienvenido Administrador: <%= usuario.getUsuario() %></h2>
<p>Acceso completo al sistema</p>
<a href="logout.jsp">Cerrar sesión</a>
