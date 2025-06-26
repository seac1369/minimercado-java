<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - MiniMercado</title>
</head>
<body>
    <h2>Iniciar Sesión</h2>
    
    <form action="LoginServlet" method="post">
        <label>Usuario:</label><br>
        <input type="text" name="usuario" required><br><br>

        <label>Contraseña:</label><br>
        <input type="password" name="contrasena" required><br><br>

        <input type="submit" value="Ingresar">
    </form>

    <%
        String error = request.getParameter("error");
        if (error != null) {
    %>
        <p style="color:red;"><%= error %></p>
    <%
        }
    %>
    
    <% if ("1".equals(request.getParameter("logout"))) { %>
    <p style="color:green;">Sesión cerrada correctamente.</p>
<% } %>

</body>
</html>
