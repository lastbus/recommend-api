<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("basePath", basePath);
%>
<% response.sendRedirect(basePath+"index"); %>
<html>
<head>
<base href="<%=basePath%>">
<title>Insert title here</title>
</head>
<body>
</body>
</html>