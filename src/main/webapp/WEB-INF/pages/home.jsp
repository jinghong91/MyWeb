<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <link href="resources/css/bootstrap.css" rel="stylesheet"/>
</head>
<body class="container">
<h1>${message}</h1>
<form:form action="${contextPath}/new">
    <table class="table">
    <tr>
        <td>NAME</td>
        <td>Joining Date</td>
        <td>Salary</td>
        <td></td>
        <td></td>
    </tr>
        <c:forEach items="${welcomeForm.userList}" var="user">
        <tr>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.enabled}</td>
            <td><a href="${contextPath}/edit-${user.id}" }>Edit</a></td>
            <td><a href="${contextPath}/delete-${user.id}" }>Delete</a></td>
        </tr>
    </c:forEach>
</table>
    <input type="submit" value="new" class="btn btn-info btn-small">
</form:form></body>
</html>