<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin</title>
    <c:set var="contextPath" value="pageContext.request.contextPath"/>
    <link href="resources/css/bootstrap.css" rel="stylesheet"/>
    <script type="text/javascript">
        function logout() {
            document.getElementById("logoutForm").submit();
        }
    </script>
</head>
<body class="container">
<c:url value="/j_spring_security_logout" var="logoutUrl"/>

<!-- csrf for log out-->
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<c:if test="${pageContext.request.userPrincipal.name!=null}">
    <h2>
        Welcome:${pageContext.request.userPrincipal.name}|<a href="javascript:logout()">Logout</a>
    </h2>
</c:if>
</body>
</html>
