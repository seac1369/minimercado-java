<%@ page import="modelo.*, java.util.*" %>
<%
    Factura factura = (Factura) request.getAttribute("factura");
    List<DetalleFactura> detalles = (List<DetalleFactura>) request.getAttribute("detalles");
%>

<html>
<head>
    <title>Detalle de Factura</title>
</head>
<body>
    <h2>Factura N° <%= factura.getId() %></h2>

    <table border="1">
        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio Unitario</th>
            <th>Subtotal</th>
        </tr>
        <% for (DetalleFactura d : detalles) { %>
            <tr>
                <td><%= d.getProducto().getNombre() %></td>
                <td><%= d.getCantidad() %></td>
                <td>$<%= d.getPrecioUnitario() %></td>
                <td>$<%= d.getCantidad() * d.getPrecioUnitario() %></td>
            </tr>
        <% } %>
        <tr>
            <td colspan="3" align="right"><strong>Total:</strong></td>
            <td><strong>$<%= factura.getTotal() %></strong></td>
        </tr>
    </table>

    <p><a href="FacturaServlet?action=listar">Volver al listado</a></p>
</body>
</html>
