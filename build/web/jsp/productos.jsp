<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Producto" %>
<%@ page import="modelo.Proveedor" %>

<jsp:useBean id="productoDAO" class="dao.ProductoDAO" scope="page" />
<%
    List<Producto> productos = productoDAO.listar();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Productos</title>
    <link rel="stylesheet" href="../css/estilos.css"><!-- opcional -->
</head>
<body>
    <h1>Listado de Productos</h1>

    <!-- ✅ Cambiado para pasar por el servlet -->
    <a href="../ProductoServlet?action=nuevo">➕ Agregar nuevo producto</a>
    <br><br>

    <table border="1" cellpadding="10">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Stock</th>
                <th>Proveedor</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (Producto p : productos) {
            %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getNombre() %></td>
                <td><%= p.getDescripcion() %></td>
                <td>$<%= p.getPrecio() %></td>
                <td><%= p.getStock() %></td>
                <td><%= p.getProveedor().getNombre() %></td>
                <td>
                    <!-- ✅ Cambiado para pasar por el servlet -->
                    <a href="../ProductoServlet?action=editar&id=<%= p.getId() %>">✏️ Editar</a>
                    |
                    <a href="../ProductoServlet?action=eliminar&id=<%= p.getId() %>" onclick="return confirm('¿Seguro que deseas eliminar este producto?')">🗑️ Eliminar</a>
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
