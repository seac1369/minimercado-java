<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.Usuario" %>
<%
    // Obtener usuario de sesión
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if (usuario == null) {
        // Si no hay usuario en sesión, redirigir a login
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }

    String rol = usuario.getRol();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Minimercado</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/estilos.css">
</head>
<body>
    <header>
        <h1>Bienvenido, <%= usuario.getUsuario() %>!</h1>
        <nav>
            <ul>
                <li><a href="<%=request.getContextPath()%>/LogoutServlet">Cerrar sesión</a></li>
            </ul>
        </nav>
    </header>

    <section>
        <h2>Panel Principal</h2>
        <% if ("admin".equals(rol)) { %>
            <h3>Menú Administrador</h3>
            <ul>
                <li><a href="<%=request.getContextPath()%>/jsp/productos/listar.jsp">Gestionar Productos</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/proveedores/listar.jsp">Gestionar Proveedores</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/facturas/listar.jsp">Ver Facturas</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/usuarios/listar.jsp">Gestionar Usuarios</a></li>
            </ul>
        <% } else if ("empleado".equals(rol)) { %>
            <h3>Menú Empleado</h3>
            <ul>
                <li><a href="<%=request.getContextPath()%>/jsp/facturas/listar.jsp">Registrar Venta / Facturar</a></li>
                <li><a href="<%=request.getContextPath()%>/jsp/productos/listar.jsp">Consultar Productos</a></li>
            </ul>
        <% } else { %>
            <p>Rol no reconocido. Contacte al administrador.</p>
        <% } %>
    </section>
</body>
</html>
