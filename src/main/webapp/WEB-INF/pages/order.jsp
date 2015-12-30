<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="order.title"/></title>
    <link href="resources/css/bootstrap.css" rel="stylesheet"/>
    <link href="resources/css/datatables.min.css" rel="stylesheet"/>
    <link href="resources/css/custom.css" rel="stylesheet"/>

    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/bootstrap.js"></script>
    <script type="text/javascript" src="resources/js/datatables.min.js"></script>

    <script type="text/javascript" class="init">
        $(document).ready(function () {
            $('#orderTable').DataTable();
        });

    </script>
</head>
<body class="container">
<jsp:include page="menu.jsp"/>
<div class="panel panel-primary">
    <div class="panel-heading">
    </div>
    <div class="panel-body">
        <table id="orderTable" class="display" cellspacing="0" width="100%">
            <thead>
            <tr role="row">
                <th><spring:message code="order.table.head.name"/></th>
                <th><spring:message code="order.table.head.client"/></th>
                <th><spring:message code="order.table.head.paymentStatu"/></th>
                <th><spring:message code="order.table.head.paidAmount"/></th>
                <th><spring:message code="order.table.head.sellWithYu"/></th>
                <th><spring:message code="order.table.head.commonDeliveryRef"/></th>
                <th><spring:message code="order.table.head.personalDeliveryRef"/></th>
                <th><spring:message code="order.table.head.priceEuro"/></th>
                <th><spring:message code="order.table.head.priceCNY"/></th>
                <th><spring:message code="order.table.head.sellPrice"/></th>
                <%--<th><spring:message code="order.table.head.profit" /></th>--%>
                <%--<th><spring:message code="order.table.head.orderDate" /></th>--%>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderItemList}" var="orderItem">
                <tr>
                    <input type="hidden" name="orderItemId" value="${orderItem.id}">
                    <td>${orderItem.name}</td>
                    <td>${orderItem.client.name}</td>
                    <td>${orderItem.paymentStatu}</td>
                    <td>${orderItem.paidAmount}</td>
                    <td>${orderItem.sellWithYu}</td>
                    <td>${orderItem.commonDelivery.refPackage}</td>
                    <td>${orderItem.personalDelivery.refPackage}</td>
                    <td>${orderItem.originPriceEuro}</td>
                    <td>${orderItem.originPriceCNY}</td>
                    <td>${orderItem.sellPrice}</td>
                        <%--<th><spring:message code="order.table.head.profit" /></th>--%>
                        <%--<th><spring:message code="order.table.head.orderDate" /></th>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>
</body>
</html>
