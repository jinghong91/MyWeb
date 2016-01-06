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
                    "<input type='hidden' name='newOrderItemList["+id+"].boughtCurrency' value='${newOrderForm.currency}'/>" +
                    "<td><input type='text' name='newOrderItemList[" + id + "].name' id='orderName_" + id + "' class='input-sm'/></td>" +
                    "<td><input type='text' name='newOrderItemList[" + id + "].originPriceEuro' id='orderPriceEuro_" + id + "' class='input-sm' value='0' size='7' maxlength='10'oninput='javascript:onInputPriceEuro("+id+",this)'/></td>" +
                    "<td><label id='labelOrderPriceCNY_"+ id + "'' >0</label></td>" +
                    "<td><input type='text' name='newOrderItemList[" + id + "].sellPrice' id='orderSellPrice_" + id + "' class='input-sm' value='0' size='7' maxlength='10' oninput='javascrip:onInputSellPrice("+id+",this)' /></td>" +
                    "<td>" +
                        "<select name='newOrderItemList["+id+"].paymentStatus' id='orderPaymentStatus_"+id+"' class='input-sm' oninput='javascript:onInputPriceEuro("+id+")'>" +
                            "<option value='notPay'><spring:message code='order.select.newOrder.notPay' /></option>"+
                            "<option value='partPaid'><spring:message code='order.select.newOrder.partPaid' /></option>"+
                            "<option value='paid' selected='true'><spring:message code='order.select.newOrder.paid' /></option>"+
                        "</select>"+
                    " </td>"+
                    "<td><input type='text' name='newOrderItemList[" + id + "].paidAmount' id='orderPaidAmount_" + id + "' class='input-sm' value='0'size='7' maxlength='10'oniput='javascrip:onlyNumber(this)'></td>" +
                    "<td>" +
                    "   <select name='newOrderItemList["+id+"].sellerList' id='orderSellerList_"+id+"' class='input-sm' multiple='true' size='2'>" +
                    "       <c:forEach items='${newOrderForm.sellerList}' var='seller'>"   +
                    "           <option value='${seller.id}' label='${seller.name}'/>" +
                    "       </c:forEach>"   +
                    "   </select>" +
                    "</td>" +
                    "<td><img alt='delete' src='<c:url value='/resources/image/cancel_icon.png' /> ' class='img-icon' onclick='javascript:deleteOrderItem("+id+")'/></td>" +
                    "</tr>");
        }
        function deleteOrderItem(id) {
            $("#newOrderRow_"+id).hide();
            $("#orderName_"+id).val(null);
        }

        function onInputPriceEuro(index,elem){
            onlyNumber(elem);
            var currency=$("#currency").val();
            updatePriceCNY(index,currency);
        }

        function updatePriceCNY(index,currency){
            var euroPrice=$("#orderPriceEuro_"+index).val();
            $("#labelOrderPriceCNY_"+index).html((euroPrice*currency).toFixed(2));
        }

        function paymentStatusUpdate(index){
            var paymentStatus= $("#orderPaymentStatus_"+index).val();
            var paidAmount= $("#orderPaidAmount_"+index);
            if(paymentStatus=="notPay"){
                paidAmount.prop( "readonly", true );
                paidAmount.attr("tabindex",-1);
                paidAmount.val(0);
            }else if(paymentStatus=="paid"){
                var sellPrice= $("#orderSellPrice_"+index).val();
                paidAmount.prop( "readonly", true );
                paidAmount.attr("tabindex",-1);
                paidAmount.val(sellPrice);
            }else if(paymentStatus=="partPaid"){
                paidAmount.prop( "readonly", false );
                paidAmount.attr("tabindex",0);
            }
        }

       function onInputSellPrice(index,elem){
           onlyNumber(elem);
           if($("#orderPaymentStatus_"+index).val()=="paid"){
               var paidAmount= $("#orderPaidAmount_"+index);
               var sellPrice= $("#orderSellPrice_"+index).val();
               paidAmount.val(sellPrice);
               paidAmount.prop( "readonly", true );
               paidAmount.attr("tabindex",0);
           }
       }

        function onlyNumber(elem) {
            var temp=elem.value.replace(/[^0-9\.]/g, '');
            if (elem.value != temp) {
                elem.value = temp;
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
        <table id="orderTable" class="table"  width="100%">
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
                <th><spring:message code="order.table.head.sellers"/></th>
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
                    <td>
                        <c:forEach items="${orderItem.sellerList}" var="seller" varStatus="loop">
                         ${seller.name}
                            <c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                    </td>
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
                <label ><spring:message code="global.currency" />:</label>
                <form:hidden path="currency" id="currency" />${newOrderForm.currency}
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
                        <th><spring:message code="order.table.head.sellers"/></th>
                        <th><img alt="add" src="<c:url value="/resources/image/add_icon.png" /> " class="img-icon" onclick="addOrderItem()"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <input type="hidden" value="0" id="index"/>
                    <tr id="newOrderRow_0">
                        <form:hidden path="newOrderItemList[0].boughtCurrency" value="${newOrderForm.currency}"/>
                        <td><form:input type="text" path="newOrderItemList[0].name" id="orderName_0" class="input-sm"/></td>
                        <td><form:input type="text" path="newOrderItemList[0].originPriceEuro" id="orderPriceEuro_0" class="input-sm numbersOnly" maxlength="10" size="7" value="0" oninput="javascript:onInputPriceEuro(0,this)"/></td>
                        <td><label id="labelOrderPriceCNY_0" >0</label></td>
                        <td><form:input type="text" path="newOrderItemList[0].sellPrice" id="orderSellPrice_0" class="input-sm" maxlength="10" size="7"  value="0" oninput="javascrip:onInputSellPrice(0,this)"/></td>
                        <td>
                            <form:select path="newOrderItemList[0].paymentStatus" id="orderPaymentStatus_0" class="input-sm" onchange="javascript:paymentStatusUpdate(0)">
                                <form:option value="notPay"><spring:message code="order.select.newOrder.notPay" /></form:option>
                                <form:option value="partPaid"><spring:message code="order.select.newOrder.partPaid" /></form:option>
                                <form:option value="paid" selected="true"><spring:message code="order.select.newOrder.paid" /></form:option>
                            </form:select>
                        </td>
                        <td><form:input type="text" path="newOrderItemList[0].paidAmount" id="orderPaidAmount_0" class="input-sm" maxlength="10" size="7" value="0" oniput='javascrip:onlyNumber(this)'/></td>
                        <td>

                            <form:select path="newOrderItemList[0].sellerList" id="orderSellerList_0" class="input-sm"  multiple="true" size="2">
                       <c:forEach items="${newOrderForm.sellerList}" var="seller">
                           <option value="${seller.id}">${seller.name}</option>
                       </c:forEach>
                            </form:select>
                        </td>
                        <td><img alt="delete" src="<c:url value="/resources/image/cancel_icon.png" /> " class="img-icon" onclick="deleteOrderItem(0)"/></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <input type="submit" class="btn btn-primary btn-sm" value="<spring:message code='global.submit' />"/>
        </form:form>
    </div></div>
</body>
</html>
