<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Producto" %>
<%@ page import="modelo.Proveedor" %>
<%@ page import="java.util.List" %>

<%
    Producto producto = (Producto) request.getAttribute("producto");
    List<Proveedor> proveedores = (List<Proveedor>) request.getAttribute("proveedores");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Producto</title>
</head>
<body>
   <fieldset>
    <legend>Editar Producto</legend>
    ...
</fieldset>

    <form action="ProductoServlet" method="post" autocomplete="off">
        <input type="hidden" name="action" value="actualizar" />
        <input type="hidden" name="id" value="<%= producto.getId() %>" />

        <label for="nombre">Nombre:</label><br>
        <input type="text" id="nombre" name="nombre" value="<%= producto.getNombre() %>" required /><br><br>

        <label for="descripcion">Descripción:</label><br>
        <textarea id="descripcion" name="descripcion" rows="4" cols="30" required><%= producto.getDescripcion() %></textarea><br><br>

        <label for="precio">Precio:</label><br>
        <input type="number" step="0.01" id="precio" name="precio" value="<%= producto.getPrecio() %>" required /><br><br>

        <label for="stock">Stock:</label><br>
        <input type="number" id="stock" name="stock" value="<%= producto.getStock() %>" required /><br><br>

        <label for="proveedor">Proveedor:</label><br>
        <select id="proveedor" name="proveedor" required>
            <% for (Proveedor p : proveedores) { %>
                <option value="<%= p.getId() %>" <%= (p.getId() == producto.getProveedor().getId()) ? "selected" : "" %>>
                    <%= p.getNombre() %>
                </option>
            <% } %>
        </select><br><br>

        <input type="submit" value="Actualizar Producto" />
    </form>

    <a href="ProductoServlet?action=listar">Cancelar</a>
</body>
</html>

