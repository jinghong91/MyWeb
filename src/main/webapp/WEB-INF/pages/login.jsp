<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="row">
    <div class="col-xs-12" align="right">
        <c:if test="${pageContext.response.locale ne 'zh'}">
            <a href="?language=zh"> <img alt="zh" src="resources/image/zh_flag.gif" height="20px"/></a>
        </c:if>
        <c:if test="${pageContext.response.locale ne 'en'}">
            <a href="?language=en"> <img alt="en" src="resources/image/en_flag.gif" height="20px"/></a>
        </c:if>
        <c:if test="${pageContext.response.locale ne 'fr'}">
            <a href="?language=fr"> <img alt="fr" src="resources/image/fr_flag.gif" height="20px"/></a>
        </c:if>
    </div>
</div>

<form action="<c:url value='/auth/login_check?targetUrl=${targetUrl}'/>" method="post">
    <div class="panel panel-default" align="center" style="margin-top: 200px">
        <div class="panel-body">
            <c:if test="${not empty error}">
                <label class="label-warning">${error}</label>
            </c:if>
            <c:if test="${not empty msg}">
                <label class="label-info">${msg}</label>
            </c:if>
            <h2><spring:message code="global.login" text="Login"/></h2>
            <div class="row">
                <div class="col-xs-12">
                    <input type="text" name="username" value="" class="input-large">
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-12">
                    <input type="password" name="password" value="" class="input-large">
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${empty loginUpdate}">
                        Remember Me: <input type="checkbox" name="remember-me" />
                    </c:if>
                    <input type="submit" name="submit" value="<spring:message code="global.submit"/>"
                           class="btn btn-primary btn-large">
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>
        Current Locale : ${pageContext.response.locale}
    </div>
</form>
