<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    function logout() {
        document.getElementById("logoutForm").submit();
    }
</script>

<nav class="navbar  navbar-inverse">
    <div class="container-fluid">
        <form action="<c:url value='/j_spring_security_logout'/>" method="post" id="logoutForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" style="padding: 2.5px 2.5px" href="#"><img alt="logo" src="resources/image/logo.gif"
                                                                               height="45px"/></a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:forEach items="${global_menuList}" var="menuItem">
                    <li><a href="<c:url value='${menuItem.url}' />"><spring:message code="${menuItem.name}"/></a></li>
                </c:forEach>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${global_menuList}" var="menuItem">
                            <li><a href="<c:url value='/${menuItem.url}' />"><spring:message
                                    code="${menuItem.name}"/></a></li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a>${global_username}</a></li>
                <li><a href="javascript:logout()">Logout</a></li>
                <li>
                    <div style="padding-top: 15px; padding-bottom: 15px;">
                        <c:if test="${pageContext.response.locale ne 'zh'}">
                            <a href="?language=zh"> <img alt="zh" src="resources/image/zh_flag.gif" height="15px"/></a>
                        </c:if>
                        <c:if test="${pageContext.response.locale ne 'en'}">
                            <a href="?language=en"> <img alt="en" src="resources/image/en_flag.gif" height="15px"/></a>
                        </c:if>
                        <c:if test="${pageContext.response.locale ne 'fr'}">
                            <a href="?language=fr"> <img alt="fr" src="resources/image/fr_flag.gif" height="15px"/></a>
                        </c:if>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>