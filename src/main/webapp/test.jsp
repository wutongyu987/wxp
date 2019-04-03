<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
    request.setAttribute("ctxPath", ctxPath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>这是一个简单的测试页面</title>
</head>
<body>
jsp测试页面

${msg}
<br/>
<c:forEach items="${list}" var="a">
    <li>${a}</li>
</c:forEach>
</body>
</html>
