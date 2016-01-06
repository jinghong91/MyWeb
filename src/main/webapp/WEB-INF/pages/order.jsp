<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

        function clientTypeChanged() {
            var newClient = $("#useNewClient").is(':checked');
            if (newClient) {
                $("#clientList").hide();
                $("#newClientName").show();
            } else {
                $("#clientList").show();
                $("#newClientName").hide();
            }
        }

        function addOrderItem() {
            var id = parseInt($("#index").val()) + 1;
            $("#index").val(id);
            $("#newOrderTable tbody").append(
                    "<tr id='newOrderRow_" + id + "'>" +
                    "<td><input type='text' name='newOrderItemList[" + id + "].name' id='orderName_" + id + "' class='input-sm'/></td>" +
                    "<td><input type='text' name='newOrderItemList[" + id + "].originPriceEuro' id='orderPriceEuro_" + id + "' class='input-sm' value='0' size='7' maxlength='10'oninput='javascript:onInputPriceEuro("+id+")'/></td>" +
                    "<td><input type='hidden' name='newOrderItemList[" + id + "].originPriceCNY' id='orderPriceCNY_" + id + "'  value='0'/></td>" +
                    "<td><input type='text' name='newOrderItemList[" + id + "].sellPrice' id='orderSellPrice_" + id + "' class='input-sm' value='0' size='7' maxlength='10'/></td>" +
                    "<td>" +
                        "<select name='newOrderItemList["+id+"].paymentStatus' id='orderPaymentStatus_"+id+"' class='input-sm' >" +
                            "<option value='notPay'><spring:message code='order.select.newOrder.notPay' /></option>"+
                            "<option value='partPaid'><spring:message code='order.select.newOrder.partPaid' /></option>"+
                            "<option value='paid' selected='true'><spring:message code='order.select.newOrder.paid' /></option>"+
                        "</select>"+
                    " </td>"+
                    "<td><input type='text' name='newOrderItemList[" + id + "].paidAmount' id='orderPaidAmount_" + id + "' class='input-sm' value='0'size='7' maxlength='10'/></td>" +
                    "<td><input type='checkbox' name='newOrderItemList[" + id + "].sellWithYu' id='orderSellWithYu_" + id + "' class='input-sm'/></td>" +
                    "<td><img alt='delete' src='<c:url value='/resources/image/cancel_icon.png' /> ' class='img-icon' onclick='javascript:deleteOrderItem("+id+")'/></td>" +
                    "</tr>");
        }
        function deleteOrderItem(id) {
            $("#newOrderRow_"+id).hide();
            $("#orderName_"+id).val(null);
        }

        function onInputCurrency(){
            var currency=$("#currency").val();
            $("#newOrderTable tbody tr").each(function(index){
                updatePriceCNY(index,currency);
            });
        }

        function onInputPriceEuro(index){
            var currency=$("#currency").val();
            updatePriceCNY(index,currency);
        }

        function updatePriceCNY(index,currency){
            var euroPrice=$("#orderPriceEuro_"+index).val();
            $("#orderPriceCNY_"+index).val(euroPrice*currency);
            $("#labelOrderPriceCNY_"+index).html(euroPrice*currency);
        }


         function numberValidation(event){

            // Allow special chars + arrows
            if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9
                    || event.keyCode == 27 || event.keyCode == 13
                    || (event.keyCode == 65 && event.ctrlKey === true)
                    || (event.keyCode >= 35 && event.keyCode <= 39)){
                return;
            }else {
                // If it's not a number stop the keypress
                if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                    event.preventDefault();
                }
            }
        }
    </script>
</head>
<body class="container">
<jsp:include page="menu.jsp"/>
<div class="panel panel-primary">
    <div class="panel-heading">
    </div>
    <div class="panel-body">
        <table id="orderTable" class="table" cellspacing="0" width="100%">
            <thead>
            <tr role="row">
                <th><spring:message code="order.table.head.name"/></th>
                <th><spring:message code="order.table.head.client"/></th>
                <th><spring:message code="order.table.head.commonDeliveryRef"/></th>
                <th><spring:message code="order.table.head.personalDeliveryRef"/></th>
                <th><spring:message code="order.table.head.priceEuro"/></th>
                <th><spring:message code="order.table.head.priceCNY"/></th>
                <th><spring:message code="order.table.head.paymentStatus"/></th>
                <th><spring:message code="order.table.head.paidAmount"/></th>
                <th><spring:message code="order.table.head.sellWithYu"/></th>
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
                    <td>${orderItem.commonDelivery.refPackage}</td>
                    <td>${orderItem.personalDelivery.refPackage}</td>
                    <td>${orderItem.originPriceEuro}</td>
                    <td>${orderItem.originPriceCNY}</td>
                    <td>${orderItem.paymentStatus}</td>
                    <td>${orderItem.paidAmount}</td>
                    <td>${orderItem.sellWithYu}</td>
                    <td>${orderItem.sellPrice}</td>
                        <%--<th><spring:message code="order.table.head.profit" /></th>--%>
                        <%--<th><spring:message code="order.table.head.orderDate" /></th>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>
<div class="panel panel-primary">
    <div class="panel-heading">
        <label class="text-primary"><spring:message code="order.label.newOrder"/> </label>
    </div>
    <div class="panel-body">
        <c:url value="/createOrder" var="newOrderAction"/>
        <form:form modelAttribute="newOrderForm" action="${newOrderAction}">
            <div class="form-group">
                <spring:message code='order.checkbox.label.newClient' var="newClient"/>
                <form:checkbox path="newClient" id="useNewClient" label="${newClient}" onchange="javascript:clientTypeChanged()"/>

                <form:select path="existedClient" class="input-sm" id="clientList">
                    <c:forEach items="${newOrderForm.clientList}" var="client">
                        <form:option value="${client.id}" label="${client.name}"/>
                    </c:forEach>
                </form:select>

                <form:input cssClass="input-sm" id="newClientName" path="newClientName" cssStyle="display :none"/>
            </div>
            <div class="form-group">
                <label for="currency" ><spring:message code="global.currency" /></label>
                <input type="text" class="input-sm" value="7" id="currency"  oninput="javascript:onInputCurrency()"/>
            </div>
            <div class="form-group">
                <table class="table" id="newOrderTable">
                    <thead>
                    <tr>
                        <th><spring:message code="order.table.head.name"/></th>
                        <th><spring:message code="order.table.head.priceEuro"/></th>
                        <th><spring:message code="order.table.head.priceCNY"/></th>
                        <th><spring:message code="order.table.head.sellPrice"/></th>
                        <th><spring:message code="order.table.head.paymentStatus"/></th>
                        <th><spring:message code="order.table.head.paidAmount"/></th>
                        <th><spring:message code="order.table.head.sellWithYu"/></th>
                        <th><img alt="add" src="<c:url value="/resources/image/add_icon.png" /> " class="img-icon" onclick="addOrderItem()"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <input type="hidden" value="0" id="index"/>
                    <tr id="newOrderRow_0">
                        <td><form:input type="text" path="newOrderItemList[0].name" id="orderName_0" class="input-sm"/></td>
                        <td><form:input type="text" path="newOrderItemList[0].originPriceEuro" id="orderPriceEuro_0" class="input-sm" maxlength="10" size="7" value="0" oninput="javascript:onInputPriceEuro(0)"/></td>
                        <td>
                            <form:hidden path="newOrderItemList[0].originPriceCNY" id="orderPriceCNY_0" value="0"/>
                            <label id="labelOrderPriceCNY_0" >0</label>
                        </td>
                        <td><form:input type="text" path="newOrderItemList[0].sellPrice" id="orderSellPrice_0" class="input-sm" maxlength="10" size="7"  value="0"/></td>
                        <td>
                            <form:select path="newOrderItemList[0].paymentStatus" id="orderPaymentStatus_0" class="input-sm" >
                                <form:option value="notPay"><spring:message code="order.select.newOrder.notPay" /></form:option>
                                <form:option value="partPaid"><spring:message code="order.select.newOrder.partPaid" /></form:option>
                                <form:option value="paid" selected="true"><spring:message code="order.select.newOrder.paid" /></form:option>
                            </form:select>
                        </td>
                        <td><form:input type="text" path="newOrderItemList[0].paidAmount" id="orderPaidAmount_0" class="input-sm" maxlength="10" size="7" value="0"/></td>
                        <td><form:checkbox path="newOrderItemList[0].sellWithYu" id="orderSellWithYu_0" class="input-sm"/></td>
                        <td><img alt="delete" src="<c:url value="/resources/image/cancel_icon.png" /> " class="img-icon" onclick="deleteOrderItem(0)"/></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <input type="submit" class="btn btn-primary btn-sm" value="<spring:message code='global.submit' />"/>
        </form:form>
    </div>
</body>
</html>
