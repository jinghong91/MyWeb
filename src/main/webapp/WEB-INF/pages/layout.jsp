<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="${pageTitle}"/></title>
    <tiles:insertAttribute name="styleCss" ignore="true"/>
    <tiles:insertAttribute name="commonJS" ignore="true"/>
</head>
<body class="container">
<tiles:insertAttribute name="header" ignore="true"/>
<tiles:insertAttribute name="body"/>
</body>
</html>