<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Welcome</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link href="/resources/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
<h1>Hello : ${message}</h1>
<form:form action="${contextPath}/new">
<table>
    <tr>
        <td>NAME</td><td>Joining Date</td><td>Salary</td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
</table>
<input type="submit" value="new">
</form:form></body>
</html>