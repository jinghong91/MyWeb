<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <script type="text/javascript" class="init">
        var error="";
        $(document).ready(function () {
            $('#orderTable').DataTable({
                "language": {
                    "url": "/resources/locales/datatable_${pageContext.response.locale}.json"
                }
            });
            var totalPriceEuro=0;
            var totalPriceCNY=0;
            var totalSellPrice=0;
            $('#addedOrdersTable  tr').each(function(){
                totalPriceEuro+=parseFloat($(this).find("td[id^=originPriceEuro_]").html());
                totalPriceCNY+=parseFloat($(this).find("td[id^=originPriceCNY_]").html());
                totalSellPrice+=parseFloat($(this).find("td[id^=sellPrice_]").html());
            });
            $("#totalPriceEuro").append(totalPriceEuro.toFixed(2));
            $("#totalPriceCNY").append(totalPriceCNY.toFixed(2));
            $("#totalSellPrice").append(totalSellPrice.toFixed(2));
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

        function addOrder() {
            var id = parseInt($("#index").val()) + 1;
            $("#index").val(id);
            $("#newOrderTable tbody").append(
                    "<tr id='newOrderRow_" + id + "'>" +
                    "<input type='hidden' name='newOrderList["+id+"].boughtCurrency' value='${createOrderForm.currency}'/>" +
                    "<td><input type='text' name='newOrderList[" + id + "].name' id='orderName_" + id + "' class='input-sm'/></td>" +
                    "<td><input type='text' name='newOrderList[" + id + "].originPriceEuro' id='orderPriceEuro_" + id + "' class='input-sm' value='0' size='7' maxlength='10'oninput='javascript:onInputPriceEuro("+id+",this)'/></td>" +
                    "<td><label id='labelOrderPriceCNY_"+ id + "'' >0</label></td>" +
                    "<td><input type='text' name='newOrderList[" + id + "].sellPrice' id='orderSellPrice_" + id + "' class='input-sm' value='0' size='7' maxlength='10' oninput='javascrip:onInputSellPrice("+id+",this)' /></td>" +
                    "<td>" +
                        "<select name='newOrderList["+id+"].paymentStatus' id='orderPaymentStatus_"+id+"' class='input-sm' oninput='javascript:paymentStatusUpdate("+id+")'>" +
                    "       <c:forEach items='${createOrderForm.paymentStatusList}' var='paymentStatus'>   "+
                    "           <option value='${paymentStatus}'><spring:message code='order.paymentStatus.${paymentStatus}' /></option>"+
                    "       </c:forEach>"+
                    "   </select>"+
                    " </td>"+
                    "<td><input type='text' name='newOrderList[" + id + "].paidAmount' id='orderPaidAmount_" + id + "' class='input-sm' value='0'size='7' maxlength='10'oniput='javascrip:onlyNumber(this)'></td>" +
                    "<td>" +
                    "   <select name='newOrderList["+id+"].sellerList' id='orderSellerList_"+id+"' class='input-sm' multiple='true' size='2'>" +
                    "       <c:forEach items='${createOrderForm.sellerList}' var='seller'>"   +
                    "           <option value='${seller.id}' label='${seller.name}'/>" +
                    "       </c:forEach>"   +
                    "   </select>" +
                    "</td>" +
                    "<td><img alt='delete' src='<c:url value='/resources/image/cancel_icon.png' /> ' class='img-icon' onclick='javascript:deleteOrder("+id+")'/></td>" +
                    "</tr>");
        }

        function clientValid(){
            var newName=$("#newClientName");
            var clientList=$("#clientList");
            newName .removeClass("input-error");
            clientList.removeClass("input-error");
            if($("#useNewClient").is(':checked')){
                var name= newName.val();
                if(name==""){
                     error="<p><spring:message code="createOrder.error.newClientEmpty"/></p>";
                    newName.addClass("input-error");
                }else{
                    $("#clientList>option").each(function(){
                      if($(this).html()==name){
                          error="<p><spring:message code="createOrder.error.clientExisted" /></p>";
                          $("#newClientName").addClass("input-error");
                          return false;
                      }
                    });
                }
            }else{
                if(clientList.val()===0){
                     error="<p><spring:message code="createOrder.error.clientEmpty"/></p>";
                    clientList.addClass("input-error");
                }
            }
        }

        function addOrderValid(){
            $("#newOrderTable tbody tr:visible").each(function(){
                var orderName=$(this).find("[id^=orderName]");
                var priceEuro=$(this).find("[id^=orderPriceEuro]");
                var sellPrice=$(this).find("[id^=orderSellPrice]");
                var paidAmount=$(this).find("[id^=orderPaidAmount]");
                var sellers=$(this).find("[id^=orderSellerList]");
                var paymentStatus=$(this).find("[id^=orderPaymentStatus]");
                orderName.removeClass("input-error");
                priceEuro.removeClass("input-error");
                sellPrice.removeClass("input-error");
                paidAmount.removeClass("input-error");
                sellers.removeClass("input-error");

                if($.trim(orderName.val())==""){
                    error="<p><spring:message code="createOrder.error.orderInfoEmpty"/></p>";
                    $(orderName).addClass("input-error");
                }
                if(priceEuro.val()==""||priceEuro.val()==0){
                    error="<p><spring:message code="createOrder.error.orderInfoEmpty"/></p>";
                    priceEuro.addClass("input-error");
                }
                if(sellPrice.val()==""||sellPrice.val()==0){
                    error="<p><spring:message code="createOrder.error.orderInfoEmpty"/></p>";
                    sellPrice.addClass("input-error");
                }
                if(paymentStatus.val()=="partPaid"){
                    if(paidAmount.val()==""){
                    error="<p><spring:message code="createOrder.error.orderInfoEmpty"/></p>";
                    paidAmount.addClass("input-error");
                    }else if(paidAmount.val()==0||paidAmount.val()>=sellPrice.val()){
                        error+="<p><spring:message code="createOrder.error.invalidPaidAmount"/></p>";
                        paidAmount.addClass("input-error");
                        paymentStatus.addClass("input-error");
                    }
                }
                if(sellers.val()==null){
                    error="<p><spring:message code="createOrder.error.orderInfoEmpty"/></p>";
                    sellers.addClass("input-error");
                }
            });

            return error;
        }

        function deleteOrder(id) {
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

        function submitForm(method){
            var formAction ="<c:url value="/createOrder"/>" +"/"+method;
            $("#createOrderForm").attr("action",  formAction);
            console.log( $("#createOrderForm").attr("action"));
            $("#createOrderForm").submit();
        }

        function deleteOrder(index){
            $("#confirmDeleteModal").on('click', "#deleteConfirmBtn", function() {
                submitForm("delete?deletedOrders="+index);
        });
            $("#confirmDeleteModal").modal("show");
        }

        function saveOrders(){
            submitForm("save");
        }

        function checkSellPrice(){
            var valid=true;
            $("#newOrderTable tbody tr:visible").each(function(){
                var sellPrice= parseInt($(this).find("[id^=orderSellPrice]").val());
                var orderPrice= parseFloat($(this).find("[id^=labelOrderPriceCNY]").html());
                if(sellPrice<orderPrice){
                  valid=false;
                    return false;
                }
            });
            return valid;
        }

        function createOrders(){
            $("#error").empty();
                clientValid();
                addOrderValid();
                if(error==""){
                    if(!checkSellPrice()){
                        $("#confirmSellPriceModal").modal("show");
                    }else{
                        submitForm("add");
                    }
                }else{
                    $("#error").append(error);
                }
        }
    </script>


<div id="error" class="text-error"></div>
<form:form modelAttribute="createOrderForm" action="">
    <div class="panel panel-primary">
        <div class="panel-heading">
           <spring:message code="order.label.newOrder"/>
        </div>
        <div class="panel-body">
            <div class="form-inline">
                <div class="form-group" style="padding-right: 10px">
                    <spring:message code='order.checkbox.label.newClient' var="newClient"/>
                    <form:checkbox path="newClient" id="useNewClient" label="${newClient}" onchange="javascript:clientTypeChanged()"/>
                    <form:select path="existedClient" class="input-sm" id="clientList" >
                        <c:forEach items="${createOrderForm.clientList}" var="client">
                            <form:option value="${client.id}" label="${client.name}"/>
                        </c:forEach>
                    </form:select>

                    <form:input cssClass="input-sm" id="newClientName" path="newClientName" cssStyle="display :none"/>
                </div>
                <div class="form-group">
                    <label ><spring:message code="global.currency" />:</label>
                    <form:hidden path="currency" id="currency" />${createOrderForm.currency}
                </div>
            </div>
            <div class="table-responsive">
                    <table class="table" id="newOrderTable">
                        <thead>
                        <tr>
                            <th><spring:message code="order.name"/></th>
                            <th><spring:message code="order.priceEuro"/></th>
                            <th><spring:message code="order.priceCNY"/></th>
                            <th><spring:message code="order.sellPrice"/></th>
                            <th><spring:message code="order.paymentStatus"/></th>
                            <th><spring:message code="order.paidAmount"/></th>
                            <th><spring:message code="order.sellers"/></th>
                            <th><img alt="add" src="<c:url value="/resources/image/add_icon.png" /> " class="img-icon" onclick="addOrder()"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <input type="hidden" value="0" id="index"/>
                        <tr id="newOrderRow_0">
                            <form:hidden path="newOrderList[0].boughtCurrency" value="${createOrderForm.currency}"/>
                            <td><form:input type="text" path="newOrderList[0].name" id="orderName_0" class="input-sm"/></td>
                            <td><form:input type="text" path="newOrderList[0].originPriceEuro" id="orderPriceEuro_0" class="input-sm numbersOnly" maxlength="10" size="7" value="0" data-rule-number="true"
                                            data-rule-range="[-20,40]" oninput="javascript:onInputPriceEuro(0,this)"/></td>
                            <td><label id="labelOrderPriceCNY_0" >0</label></td>
                            <td><form:input type="text" path="newOrderList[0].sellPrice" id="orderSellPrice_0" class="input-sm" maxlength="10" size="7"  value="0" oninput="javascrip:onInputSellPrice(0,this)"/></td>
                            <td>
                                <form:select path="newOrderList[0].paymentStatus" id="orderPaymentStatus_0" class="input-sm" onchange="javascript:paymentStatusUpdate(0)">
                                    <c:forEach items="${createOrderForm.paymentStatusList}" var="paymentStatus">
                                        <form:option value="${paymentStatus}"><spring:message code="order.paymentStatus.${paymentStatus}" /></form:option>
                                    </c:forEach>
                                </form:select>
                            </td>
                            <td><form:input type="text" path="newOrderList[0].paidAmount" id="orderPaidAmount_0" class="input-sm" maxlength="10" size="7" value="0" oniput='javascrip:onlyNumber(this)'/></td>
                            <td>
                                <form:select path="newOrderList[0].sellerList" id="orderSellerList_0" class="input-sm"  multiple="true" size="2">
                                    <c:forEach items="${createOrderForm.sellerList}" var="seller">
                                        <option value="${seller.id}">${seller.name}</option>
                                    </c:forEach>
                                </form:select>
                            </td>
                            <td><img alt="delete" src="<c:url value="/resources/image/cancel_icon.png" /> " class="img-icon" onclick="deleteOrder(0)"/></td>
                        </tr>
                        </tbody>
                    </table>
            </div>
            <input type="button" class="btn btn-primary btn-sm" value="<spring:message code='global.add' />" onclick="javascript:createOrders()"/>
        </div>
    </div>
    <c:if test="${not empty addedOrderMap}">
        <div class="panel panel-primary">
            <div class="panel-body">
                <input type="hidden" id="deletedOrders" value=""/>
                <div class="table-responsive">
                    <table  width="100%" style="border-bottom: solid ;">
                        <tr bgcolor="#a9a9a9" >
                            <td >
                                <table class="table  table-condensed" >
                                    <colgroup>
                                        <col width="13%">
                                        <col width="11%">
                                        <col width="14%">
                                        <col width="15%">
                                        <col width="11%">
                                        <col width="11%">
                                        <col width="11%">
                                        <col width="11%">
                                        <col width="3%">
                                    </colgroup>
                                    <tr >
                                        <th class="text-center"><spring:message code="order.client"/></th>
                                        <th class="text-center"><spring:message code="order.name"/></th>
                                        <th class="text-center"><spring:message code="order.priceEuro"/></th>
                                        <th class="text-center"><spring:message code="order.priceCNY"/></th>
                                        <th class="text-center"><spring:message code="order.sellers"/></th>
                                        <th class="text-center"><spring:message code="order.paymentStatus"/></th>
                                        <th class="text-center"><spring:message code="order.paidAmount"/></th>
                                        <th class="text-center"><spring:message code="order.sellPrice"/></th>
                                        <th class="text-center"></th>
                                    </tr>
                                </table>
                            </td>
                            <td width="18px"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div style=" max-height:500px; overflow:auto;">
                                    <table class="table table-striped table-condensed " id="addedOrdersTable">
                                        <colgroup>
                                            <col width="13%">
                                            <col width="11%">
                                            <col width="14%">
                                            <col width="15%">
                                            <col width="11%">
                                            <col width="11%">
                                            <col width="11%">
                                            <col width="11%">
                                            <col width="3%">
                                        </colgroup>
                                        <c:forEach items="${addedOrderMap}" var="order" varStatus="loop">
                                            <tr id="addedOrder_${order.key}" class="text-center">
                                                <td id="clientName_${order.key}">${order.value.client.name}</td>
                                                <td id="orderName_${order.key}">${order.value.name}</td>
                                                <td id="originPriceEuro_${order.key}">${order.value.originPriceEuro}</td>
                                                <td id="originPriceCNY_${order.key}">${order.value.originPriceCNY}</td>
                                                <td id="sellPrice_${order.key}">${order.value.sellPrice}</td>
                                                <td id="paymentStatus_${order.key}"><spring:message code="order.select.newOrder.${order.value.paymentStatus}"/></td>
                                                <td id="paidAmount_${order.key}">${order.value.paidAmount}</td>
                                                <td id="sellerList_${order.key}">
                                                    <c:forEach items="${order.value.sellerList}" var="seller" varStatus="loop">
                                                        ${seller.name}
                                                        <c:if test="${!loop.last}">,</c:if>
                                                    </c:forEach>
                                                </td>
                                                <td><img src="<c:url value="/resources/image/delete_icon.png" />" class="img-icon" alt="delete" onclick="javascript:deleteOrder(${order.key})"/></td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="row text-center" >
                    <div class="col-sm-4" >
                        <label id="totalPriceEuro"><spring:message code="order.totalPriceEuro" /> : </label>
                    </div>
                    <div class="col-sm-4" >
                        <label id="totalPriceCNY"><spring:message code="order.totalPriceCNY" /> :</label>
                    </div>
                    <div class="col-sm-4" >
                        <label id="totalSellPrice"><spring:message code="order.totalSellPrice" /> : </label>
                    </div>
                </div>
                <input type="button" value="<spring:message code="global.submit"/>" class="btn btn-primary btn-sm" onclick="javascript:saveOrders()">
            </div>
        </div>
    </c:if>
</form:form>
<div class="modal " id="confirmDeleteModal" tabindex="-1"  >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <a class="close" data-dismiss="modal" >&times;</a>
                    <label><spring:message code="global.confirm"/></label>
                    </div>
                </div>
            <div class="modal-body">
                <label><spring:message code="createOrder.message.deleteConfirm"/></label>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary btn-ok"  data-dismiss="modal" id="deleteConfirmBtn"><spring:message code="global.delete"/></button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" ><spring:message code="global.cancel"/></button>
            </div>
        </div>
    </div>
</div>
<div class="modal " id="confirmSellPriceModal" tabindex="-1"  >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <a class="close" data-dismiss="modal" >&times;</a>
                    <label><spring:message code="global.confirm"/></label>
                </div>
            </div>
            <div class="modal-body">
                <label><spring:message code="createOrder.message.sellPriceConfirm"/></label>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary btn-ok"  data-dismiss="modal" id="sellPriceConfirmBtn" onclick="javascript:submitForm('add')">
                    <spring:message code="global.continue"/>
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" >
                    <spring:message code="global.cancel"/>
                </button>
            </div>
        </div>
    </div>
</div>