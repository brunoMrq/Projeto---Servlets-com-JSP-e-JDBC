<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tela que Mostra os erros</title>
</head>
<body>
<h1>Mensagem de Erro !!!</h1>
<h2>Entre em Contato com a equipe de suporte do Sistema</h2>

<%
out.print(request.getAttribute("msg"));
%>
</body>
</html>