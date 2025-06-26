<%@ page import="java.util.*, modelo.Factura" %>
<%
    List<Factura> facturas = (List<Factura>) request.getAttribute("facturas");
%>

<html>
<head>
    <title>Listado de Facturas</title>
</head>
<body>
    <h2>Historial de Facturas</h2>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Total</th>
            <th>Acción</th>
        </tr>
        <% for (Factura f : facturas) { %>
            <tr>
                <td><%= f.getId() %></td>
                <td><%= f.getFecha() %></td>
                <td>$<%= f.getTotal() %></td>
                <td><a href="FacturaServlet?action=detalle&id=<%= f.getId() %>">Ver Detalle</a></td>
            </tr>
        <% } %>
    </table>
</body>
</html>
