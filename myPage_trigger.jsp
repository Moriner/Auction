<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
session.setAttribute("enable", "true");
session.setAttribute("my_choice", "1");
response.sendRedirect("index.jsp");%>
</body>
</html>