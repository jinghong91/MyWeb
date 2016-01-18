<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" class="init">
        $(document).ready(function () {
            $('#orderTable').DataTable({
                "language": {
                    "url": "/resources/locales/datatable_${pageContext.response.locale}.json"
                }
            });

            $('#filterCreateDate').datepicker({
                format: "<spring:message code="global.datepicker.dateFormat"/>",
                todayBtn: "linked",
                language: "${pageContext.response.locale}",
                todayHighlight: true,
                calendarWeeks: true,
            });
        });
        function  selectOrder(row,id){
            $("#orderTable tbody tr.selected").removeClass("selected");
           $(row).addClass("selected");
            $.ajax({
                type:"POST",
                url:"<c:url value="/ajax/order/getOrder"/>?id="+id,
                timeout:100000,
                success:function(data){
                    cancelEdit();
                    refreshDetailPanel(data);
                    refreshOrderDetailInfo(data);
                   
                        refreshCommonDeliveryInfo(data.commonDelivery);
                    
                        refreshPersonalDeliveryInfo

                    $("#orderDetailsPanel").show();
                }
            });
        }

        function refreshOrderDetailInfo(order){
            $("#nameInfo  span").empty().append(order.name);
            $("#createDateInfo  span").empty().append($.datepicker.formatDate("<spring:message code="global.jquery.dateFormat"/>", new Date(order.createDate)));
            $("#boughtCurrencyInfo  span").empty().append(order.boughtCurrency);
            $("#priceEuroInfo  span").empty().append(order.originPriceEuro);
            $("#priceCNYInfo  span").empty().append(order.originPriceCNY);
            $("#sellPriceInfo  span").empty().append(order.sellPrice);
            var paymentStatus="";
            switch (order.paymentStatus) {
                case 'notPay':
                    paymentStatus="<spring:message code="order.paymentStatus.notPay" />"
                    break;
                case 'partPaid':
                    paymentStatus="<spring:message code="order.paymentStatus.partPaid" />"
                    break;
                case 'paid':
                    paymentStatus="<spring:message code="order.paymentStatus.paid" />"
                    break;
            }
            $("#paymentStatusInfo  span").empty().append(paymentStatus);
            $("#paidAmountInfo  span").empty().append(order.paidAmount);
            var sellers="";
            $.each(order.sellerList,function(index, seller){
                sellers+=","+seller.name;
            });
            sellers=sellers.replace(",","");
            $("#sellersInfo span").empty().append(sellers);
        }

        function refreshCommonDeliveryInfo(commonDelivery){
            $("#deliveryTypeInfo span").empty();
            $("#senDateInfo span").empty();
            $("#commonDeliveryFeeInfo span").empty();
            $("#taxRefundInfo span").empty();
            $("#tariffInfo span").empty();
            if(commonDelivery!=null){
                $("#deliveryTypeInfo span").append(commonDelivery.type);
                $("#senDateInfo span").append(commonDelivery.sendDate);
                $("#commonDeliveryFeeInfo span").append(commonDelivery.deliveryFee);
                $("#taxRefundInfo span").append(commonDelivery.taxRefundPercentage);
                $("#tariffInfo span").append(commonDelivery.tariffRate);
            }
        }

        function refreshPersonalDeliveryInfo(personDelivery){
            $("#packageRefInfo span").empty().append(personDelivery.refPackage);
            $("#deliveryStatusInfo span").empty().append(personDelivery.status);
           if(personDelivery.address!=null){
               refreshAddressInfo(personDelivery.address);
           }
        }

        function refreshAddressInfo(address){
            $("#receiverInfo span").empty().append(address.receiver);
            $("#addressInfo span").empty().append(address.address);
            $("#phoneInfo span").empty().append(address.phoneNumber);
        }

        function refreshDetailPanel(order){
            $("#orderId").val(order.id);
            $("#clientId").val(order.client.id);
            $("#personalDeliveryId").val(order.personalDelivery.id);
            if(order.commonDelivery!=null){
                $("#commonDeliveryId").val(order.commonDelivery.id);
            }
            $("#createDate").val(new Date(order.createDate));
            $("#boughtCurrency").val(order.boughtCurrency);
            $("#name").val(order.name);
            $("#originPriceEuro").val(order.originPriceEuro);
            $("#sellPrice").val(order.sellPrice);
            $("#paymentStatus").val(order.paymentStatus);
            $("#paidAmount").val(order.paidAmount);
            $.each(order.sellerList,function(index, seller){
                $("#sellerList option[value="+seller.id+"]")  .prop('selected', true);
            });
        }

        function editOrder(){
            $("#divOrderDetailInfo").hide();
            $("#divEditOrder").show();
        }

        function cancelEdit(){
            $("#divEditOrder").hide();
            $("#divOrderDetailInfo").show();
        }

        function submitForm(method){
            $("#orderManagementForm").attr("action","<c:url value="/orderManagement"/>"+"/"+method);
            $("#orderManagementForm").submit();
        }

        function onInputPriceEuro(elem){
            onlyNumber(elem);
            var currency=$("#currency").val();
            updatePriceCNY(currency);
        }

        function updatePriceCNY(currency){
            var euroPrice=$("#priceEuro").val();
            $("#orderPriceCNY").html((euroPrice*currency).toFixed(2));
        }

        function paymentStatusUpdate(){
            var paymentStatus= $("#paymentStatus").val();
            var paidAmount= $("#paidAmount");
            if(paymentStatus=="notPay"){
                paidAmount.prop( "readonly", true );
                paidAmount.attr("tabindex",-1);
                paidAmount.val(0);
            }else if(paymentStatus=="paid"){
                var sellPrice= $("#sellPrice").val();
                paidAmount.prop( "readonly", true );
                paidAmount.attr("tabindex",-1);
                paidAmount.val(sellPrice);
            }else if(paymentStatus=="partPaid"){
                paidAmount.prop( "readonly", false );
                paidAmount.attr("tabindex",0);
            }
        }

        function onInputSellPrice(elem){
            onlyNumber(elem);
            if($("#paymentStatus").val()=="paid"){
                var paidAmount= $("#paidAmount");
                var sellPrice= $("#sellPrice").val();
                paidAmount.val(sellPrice);
                paidAmount.prop( "readonly", true );
                paidAmount.attr("tabindex",0);
            }
        }


        function resetFilters(){
            $("#filterClient").val("");
            $("#filterPaymentStatus").val("");
            $("#filterCreateDateFrom").val("");
            $("#filterCreateDateTo").val("");
            $("#filterSeller").val("0");
        }
        </script>

<form:form modelAttribute="orderManagementForm" id="orderManagementForm">
<div class="panel panel-primary">
    <div class="panel-heading">
        <spring:message code="createOrder.panel.orderList" />
    </div>
    <div class="panel-body">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <spring:message code="global.filter"/>
            </div>
            <div class="panel-body form-inline">
                <div class="form-group">
                    <label for="filterClient"><spring:message code="order.client"/></label>
                    <form:select path="filterClientId" id="filterClient" cssClass="input-sm form-control" >
                        <form:option value="0" ><spring:message code="global.all"/></form:option>
                        <form:options items="${orderManagementForm.clientList}"  itemValue="id" itemLabel="name" />
                    </form:select>
                </div>
                <div class="form-group">
                    <label for="filterPaymentStatus"><spring:message code="order.paymentStatus"/></label>
                    <form:select path="filterPaymentStatus" id="filterPaymentStatus" cssClass="input-sm form-control" >
                        <form:option value="" ><spring:message code="global.all"/></form:option>
                        <c:forEach items="${orderManagementForm.paymentStatusList}" var="paymentStatus">
                            <form:option value="${paymentStatus}"><spring:message code="order.paymentStatus.${paymentStatus}" /></form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="form-group" >
                    <label for="filterCreateDate" ><spring:message code="order.createDate"/></label>
                    <div class="form-group" >
                        <div class="input-daterange input-group "  id="filterCreateDate" >
                            <form:input type="text" path="filterCreateDateFrom" id="filterCreateDateFrom" class="input-sm form-control"  size="7" />
                            <span class="input-group-addon"><spring:message code="global.to"/></span>
                            <form:input type="text" path="filterCreateDateTo" id="filterCreateDateTo"  class="input-sm form-control" size="7" />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="filterSeller"><spring:message code="order.sellers"/></label>
                    <form:select path="filterSellerId" id="filterSeller"  class="input-sm form-control">
                        <form:option value="0" ><spring:message code="global.all"/></form:option>
                        <form:options items="${orderManagementForm.sellerList}" itemLabel="name" itemValue="id" />
                    </form:select>
                </div>
                <div class="form-group pull-right">
                    <input type="button" class="btn btn-primary btn-sm" value="<spring:message code="global.reset" />" onclick="javascript:resetFilters()"/>
                    <input type="button" class="btn btn-primary btn-sm" value="<spring:message code="global.filter"/>" onclick="javascript:submitForm('filter')" />
                </div>

            </div>
        </div>
        <table id="orderTable" class="table table-hover"  width="100%">
            <thead>
            <tr role="row">
                <th><spring:message code="order.name"/></th>
                <th><spring:message code="order.client"/></th>
                <th><spring:message code="order.priceEuro"/></th>
                <th><spring:message code="order.priceCNY"/></th>
                <th><spring:message code="order.sellPrice"/></th>
                <th><spring:message code="order.paymentStatus"/></th>
                <th><spring:message code="order.paidAmount"/></th>
                <th><spring:message code="order.sellers"/></th>
                <th><spring:message code="order.createDate" /></th>
            </tr>
            </thead>
            <tbody>
            <c:set  var="datePattern"><spring:message code='global.java.dateFormat' /></c:set>
            <c:forEach items="${orderManagementForm.orderList}" var="order" varStatus="loop">
                <tr onclick="javascript:selectOrder(this,${order.id})">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <td>${order.name}</td>
                    <td>${order.client.name}</td>
                    <td>${order.originPriceEuro}</td>
                    <td>${order.originPriceCNY}</td>
                    <td>${order.sellPrice}</td>
                    <td><spring:message code="order.paymentStatus.${order.paymentStatus}" /></td>
                    <td>${order.paidAmount}</td>
                    <td>
                        <c:forEach items="${order.sellerList}" var="seller" varStatus="loop">
                            ${seller.name}
                            <c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                    </td>
                        <%--<th><spring:message code="order.table.head.profit" /></th>--%>
                    <td>
                        <fmt:formatDate value="${order.createDate}" pattern="${datePattern}"  />
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <label><spring:message code="global.totalSellPrice" />:</label>${orderManagementForm.totalSellAmount}
    </div>
</div>
<div class="panel panel-primary" id="orderDetailsPanel" style="display: none" >
    <div class="panel-heading">
        <spring:message code="createOrder.panel.orderDetail" />
    </div>
    <div class="panel-body" >
        <div class="col-lg-4"  id="divEditOrder"  style="display: none">
                <div class="panel panel-primary" >
                    <div class="panel-heading">
                        <spring:message code="createOrder.panel.editOrder" />
                        <span style="float:right">
                            <span  class="glyphicon glyphicon-ok" aria-hidden="true" onclick="javascript:submitForm('update')" data-toggle="tooltip" data-placement="left" title="<spring:message code="global.confirm"/>" ></span>
                            <span  class="glyphicon glyphicon-remove" aria-hidden="true" onclick="javascript:cancelEdit()" data-toggle="tooltip" data-placement="left" title="<spring:message code="global.cancel"/>" ></span>
                        </span>
                    </div>
                    <div class="panel-body">
                            <form:hidden id="orderId" path="selectedOrder.id" />
                            <form:hidden id="personalDeliveryId" path="selectedOrder.personalDelivery.id" />
                            <form:hidden id="commonDeliveryId" path="selectedOrder.commonDelivery.id" />
                            <form:hidden id="boughtCurrency" path="selectedOrder.boughtCurrency" />
                            <form:hidden id="createDate" path="selectedOrder.createDate" />
                            <div  class="form-group">
                                <label for="clientId"><spring:message code="order.client"/></label>
                                <form:select path="selectedOrder.client.id" id="clientId" class="input-sm"  >
                                    <form:options items="${orderManagementForm.clientList}"  itemValue="id" itemLabel="name" />
                                </form:select>
                            </div>
                            <div  class="form-group">
                                <label for="name"><spring:message code="order.name"/></label>
                                <form:input type="text" path="selectedOrder.name" id="name"  />
                            </div>
                            <div  class="form-group">
                                <label for="originPriceEuro"><spring:message code="order.priceEuro" /></label>
                                <form:input type="text" path="selectedOrder.originPriceEuro" id="originPriceEuro" oninput="javascript:onInputPriceEuro(this)" />
                            </div>
                            <div  class="form-group">
                                <label for="sellPrice"><spring:message code="order.sellPrice"/></label>
                                <form:input type="text" path="selectedOrder.sellPrice" id="sellPrice" oninput="javascrip:onInputSellPrice(this)" />
                            </div>
                            <div  class="form-group">
                                <label for="paymentStatus"><spring:message code="order.paymentStatus"/></label>
                                <form:select path="selectedOrder.paymentStatus" id="paymentStatus" class="input-sm" onchange="javascript:paymentStatusUpdate()" >
                                    <c:forEach items="${orderManagementForm.paymentStatusList}" var="paymentStatus">
                                        <form:option value="${paymentStatus}"><spring:message code="order.paymentStatus.${paymentStatus}" /></form:option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div  class="form-group">
                                <label for="paidAmount"><spring:message code="order.paidAmount"/></label>
                                <form:input type="text" path="selectedOrder.paidAmount" id="paidAmount"  />
                            </div>
                            <div  class="form-group">
                                <label for="sellerList"><spring:message code="order.sellers"/></label>
                                <form:select path="selectedOrder.sellerList" id="sellerList" class="input-sm"  multiple="true" size="2">
                                    <c:forEach items="${orderManagementForm.sellerList}" var="seller">
                                        <option value="${seller.id}">${seller.name}</option>
                                    </c:forEach>
                                </form:select>
                            </div>
                    </div>
                </div>
        </div>
        <div class="col-lg-4" id="divOrderDetailInfo">
            <div class="panel panel-primary "  >
                <div class="panel-heading">
                    <spring:message code="createOrder.panel.order"/>
                    <span style="float:right" class="glyphicon glyphicon-edit" aria-hidden="true" onclick="javascript:editOrder()" data-toggle="tooltip" data-placement="left" title="<spring:message code="global.edit"/>"/>
                </div>
                <div class="panel-body">
                    <div  class="form-group" id="nameInfo">
                        <label ><spring:message code="order.name"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="createDateInfo">
                        <label ><spring:message code="order.createDate"/> : </label><span></span>
                    </div>

                    <div class="form-group" id="boughtCurrencyInfo">
                        <label ><spring:message code="global.currency"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="priceEuroInfo">
                        <label ><spring:message code="order.priceEuro"/> :</label><span></span>
                    </div>
                    <div  class="form-group" id="priceCNYInfo">
                        <label ><spring:message code="order.sellPrice"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="sellPriceInfo">
                        <label ><spring:message code="order.sellPrice"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="paymentStatusInfo">
                        <label ><spring:message code="order.paymentStatus"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="paidAmountInfo">
                        <label ><spring:message code="order.paidAmount"/> :</label><span></span>
                    </div>
                    <div  class="form-group" id="sellersInfo">
                        <label ><spring:message code="order.sellers"/> : </label><span></span>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-lg-4">
            <div class="panel panel-primary " >
                <div class="panel-heading">
                    <spring:message code="createOrder.panel.commonDelivery"/>
                </div>
                <div class="panel-body">
                    <div  class="form-group" id="deliveryTypeInfo">
                        <label ><spring:message code="commonDelivery.type"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="senDateInfo">
                        <label ><spring:message code="commonDelivery.sendDate"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="commonDeliveryFeeInfo">
                        <label ><spring:message code="commonDelivery.deliveryFee"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="taxRefundInfo">
                        <label ><spring:message code="commonDelivery.taxRefundRate"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="tariffInfo">
                        <label ><spring:message code="commonDelivery.tariffRate"/> : </label><span></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="panel panel-primary " >
                <div class="panel-heading">
                    <label><spring:message code="createOrder.panel.personalDelivery"/></label>
                </div>
                <div class="panel-body">
                    <div  class="form-group" id="packageRefInfo">
                        <label ><spring:message code="personalDelivery.ref"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="deliveryStatusInfo">
                        <label ><spring:message code="personalDelivery.status"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="receiverInfo">
                        <label ><spring:message code="address.receiver"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="addressInfo">
                        <label ><spring:message code="address.address"/> : </label><span></span>
                    </div>
                    <div  class="form-group" id="phoneInfo">
                        <label ><spring:message code="address.phone"/> : </label><span></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</form:form>

