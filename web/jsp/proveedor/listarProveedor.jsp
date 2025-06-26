<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, modelo.Proveedor" %>
<%
    List<Proveedor> lista = (List<Proveedor>) request.getAttribute("listaProveedores");
%>
<html>
<head><title>Lista de Proveedores</title></head>
<body>
    <h2>Proveedores</h2>
    <a href="ProveedorServlet?action=nuevo">Nuevo Proveedor</a>
    <table border="1">
        <tr><th>ID</th><th>Nombre</th><th>Contacto</th><th>Dirección</th><th>Acciones</th></tr>
        <%
            for (Proveedor p : lista) {
        %>
        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getNombre() %></td>
            <td><%= p.getContacto() %></td>
            <td><%= p.getDireccion() %></td>
            <td>
                <a href="ProveedorServlet?action=editar&id=<%= p.getId() %>">Editar</a> |
                <a href="ProveedorServlet?action=eliminar&id=<%= p.getId() %>">Eliminar</a>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>

