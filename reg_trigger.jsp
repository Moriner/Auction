<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
session.setAttribute("register", "true");
session.setAttribute("register_fail", "false");
response.sendRedirect("index.jsp");%>
</body>
</html>