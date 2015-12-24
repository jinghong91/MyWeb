<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>New</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <link href="/resources/css/bootstrap.css" rel="stylesheet"/>
</head>
<body>
<form:form method="post" modelAttribute="user" cssclass="form" action="${contextPath}/add">
    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="username">Username</label>
            <div class="col-md-7">
                <form:input type="text" path="username" id="username" cssClass="form-control input-sm" />
                <form:errors path="username" cssClass="help-inline"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="password">Password</label>
            <div class="col-md-7">
                <form:input type="text" path="password" id="password" class="form-control input-sm"/>
                    <form:errors path="password" class="help-inline"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="email">Email</label>
            <div class="col-md-7">
                <form:input type="text" path="email" id="email" class="form-control input-sm"/>
                    <form:errors path="email" class="help-inline"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="form-actions floatRight">
            <input type="submit" value="Create" class="btn btn-primary btn-sm">
        </div>
    </div>
</form:form>
</body>
</html>
