<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Proveedor" %>
<%@ page import="java.util.List" %>

<%
    List<Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nuevo Producto</title>
    <style>
        label { font-weight: bold; }
        form { max-width: 400px; }
    </style>
</head>
<body>
    <h2>Agregar Nuevo Producto</h2>

    <form action="ProductoServlet" method="post">
        <input type="hidden" name="action" value="agregar" />

        <label for="nombre">Nombre:</label><br>
        <input type="text" id="nombre" name="nombre" required><br><br>

        <label for="descripcion">Descripción:</label><br>
        <textarea id="descripcion" name="descripcion" rows="4" cols="30" required></textarea><br><br>

        <label for="precio">Precio:</label><br>
        <input type="number" id="precio" name="precio" step="0.01" required><br><br>

        <label for="stock">Stock:</label><br>
        <input type="number" id="stock" name="stock" required><br><br>

        <label for="proveedor">Proveedor:</label><br>
        <select id="proveedor" name="proveedor" required>
            <option value="">-- Seleccionar proveedor --</option>
            <% if (proveedores != null) {
                   for (Proveedor p : proveedores) { %>
                <option value="<%= p.getId() %>"><%= p.getNombre() %></option>
            <%   }
               } %>
        </select><br><br>

        <input type="submit" value="Agregar Producto">
    </form>

    <br>
    <a href="ProductoServlet?action=listar">⬅ Volver a la lista</a>
</body>
</html>
