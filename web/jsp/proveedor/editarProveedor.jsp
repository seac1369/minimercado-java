<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="modelo.Proveedor" %>
<%
    Proveedor p = (Proveedor) request.getAttribute("proveedor");
%>
<html>
<head><title>Editar Proveedor</title></head>
<body>
    <h2>Editar Proveedor</h2>
    <form method="post" action="ProveedorServlet?action=actualizar">
        <input type="hidden" name="id" value="<%= p.getId() %>">
        Nombre: <input type="text" name="nombre" value="<%= p.getNombre() %>"><br>
        Contacto: <input type="text" name="contacto" value="<%= p.getContacto() %>"><br>
        Dirección: <input type="text" name="direccion" value="<%= p.getDireccion() %>"><br>
        <input type="submit" value="Actualizar">
    </form>
</body>
</html>
