<%@ page import="java.util.*, modelo.Producto" %>
<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
%>
<html>
<head><title>Nueva Factura</title></head>
<body>
    <h2>Crear Nueva Factura</h2>
    <form method="post" action="FacturaServlet">
        <input type="hidden" name="action" value="guardar">
        <table border="1">
            <tr><th>Producto</th><th>Precio</th><th>Stock</th><th>Cantidad</th></tr>
            <% for (Producto p : productos) { %>
                <tr>
                    <td><%= p.getNombre() %></td>
                    <td><%= p.getPrecio() %></td>
                    <td><%= p.getStock() %></td>
                    <td>
                        <input type="hidden" name="idProducto" value="<%= p.getId() %>">
                        <input type="number" name="cantidad" min="0" max="<%= p.getStock() %>" value="0">
                    </td>
                </tr>
            <% } %>
        </table>
        <input type="submit" value="Generar Factura">
    </form>
</body>
</html>
