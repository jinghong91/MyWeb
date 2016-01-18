<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <script>
        $(document).ready(function(){
            $('#filterCreateDate').datepicker({
                format: "<spring:message code="global.datepicker.dateFormat"/>",
                todayBtn: "linked",
                language: "${pageContext.response.locale}",
                todayHighlight: true,
                calendarWeeks: true,
            });

            $('#newSendDate ').datepicker({
                format: "<spring:message code="global.datepicker.dateFormat"/>",
                todayBtn: "linked",
                language: "${pageContext.response.locale}",
                todayHighlight: true,
                calendarWeeks: true,
            }).val($.datepicker.formatDate("<spring:message code="global.jquery.dateFormat"/>", new Date()));
        });


        function selectOrder(row){
            $(row).hasClass("selected")?$(row).removeClass("selected"):$(row).addClass("selected");
        }

        function createDelivery(){
            var taxRefund=$("#newTaxRefund").val();
            var tariff=$("#newTariff").val();
            var error="";
            $("#error").empty();
            if( !taxRefund>100){
                $("#newTaxRefund").addClass("input-error");
                <spring:message code="commonDelivery.taxRefundRate" var="labeLTaxRefundRate"/>
             error+= "<p><spring:message code="global.error.invalid" arguments="${labeLTaxRefundRate}"/></p>";
            }
            if(!tariff>100){
                $("#newTariff").addClass("input-error");
                <spring:message code="commonDelivery.tariffRate" var="labelTariff"/>
                error+= "<p><spring:message code="global.error.invalid" arguments="${labelTariff}"/></p>";
            }if(!$.isNumeric($("#newDeliveryFee").val())){
                $("#newDeliveryFee").addClass("input-error");
                <spring:message code="commonDelivery.deliveryFee" var="labelDeliveryFee"/>
                error+= "<p><spring:message code="global.error.invalid" arguments="${labelDeliveryFee}"/></p>";
            }
           var trSelectedOrders=$("#orderTable tbody tr.selected");
            if($(trSelectedOrders).length==0){
                error+= "<p><spring:message code="createCommonDelivery.error.emptyOrder" /></p>";

            }
            if(error==""){
            var selectedOrders=""
            $.each(trSelectedOrders,function(index,selectedOrder){
                selectedOrders+="_"+$(selectedOrder).find("[id^=orderId]").val();
            });
                $("#newTaxRefund").val(taxRefund/100);
                $("#newTariff").val(tariff/100);
                submitForm("create?selectedOrders="+selectedOrders);
            }else{
                $("#error").append(error);
            }
        }

        function submitForm(method){
            $("#createCommonDeliveryForm").attr("action","<c:url value="/createCommonDelivery"/>"+"/"+method);
            $("#createCommonDeliveryForm").submit();
        }

        function selectAllOrders(){
            $("#orderTable tbody tr").each(function(){
               if(! $(this).hasClass("selected")){
                   $(this).addClass("selected");
               }
            });
        }

        function resetSelectedOrders(){
            $("#orderTable tbody tr").each(function(){
                    $(this).removeClass("selected");
            });
        }

        function resetFilters(){
            $("#filterPaymentStatus").val("");
            $("#filterCreateDateFrom").val("");
            $("#filterCreateDateTo").val("");
            $("#filterSeller").val("0");
        }

        function onChangeType(){
           var newTaxRefund= $("#newTaxRefund");
            var newTariff=$("#newTariff");
            var newDeliveryFee=$("#newDeliveryFee");
            var newType=$("#newType").val();
            switch(newType){
                case "shopper":
                    newTaxRefund.val("16.667").prop("readonly",true).attr("tabindex","-1");
                    newTariff.val("2").prop("readonly",true).attr("tabindex","-1");
                    newDeliveryFee.prop("readonly",false).attr("tabindex","");
                    break;
                case "guide":
                    newTaxRefund.prop("readonly",false).attr("tabindex","");
                    newTariff.val("0").prop("readonly",true).attr("tabindex","-1");
                    newDeliveryFee.val("0").prop("readonly",true).attr("tabindex","-1");
                    break;
                case "post":
                    newTaxRefund.val("0").prop("readonly",true).attr("tabindex","-1");
                    newTariff.prop("readonly",false).attr("tabindex","");
                    newDeliveryFee.prop("readonly",false).attr("tabindex","");
                    break;
                case "other":
                    newTaxRefund.prop("readonly",false).attr("tabindex","");
                    newTariff.prop("readonly",false).attr("tabindex","");
                    newDeliveryFee.prop("readonly",false).attr("tabindex","");
                    break;
            }
        }
    </script>
<div id="error" class="text-error"></div>
<form:form modelAttribute="createCommonDeliveryForm" action="">
    <div class="panel panel-primary" id="newDeliveryPanel">
        <div class="panel-heading">
           <spring:message code="createCommonDelivery.panel.newDelivery"/>
        </div>
        <div class="panel-body form-inline">
            <div class="form-group">
                <label for="newType"><spring:message code="commonDelivery.type"/></label>
                <form:select path="newCommonDelivery.type" id="newType" class="input-sm form-control" onchange="javascript:onChangeType()">
                    <c:forEach items="${createCommonDeliveryForm.commonDeliveryTypeSet}" var="deliveryType">
                        <form:option value="${deliveryType}"><spring:message code="commonDelivery.type.${deliveryType.value}" /></form:option>
                    </c:forEach>
                </form:select>
            </div>
            <div class="form-group">
                <label for="newSendDate"><spring:message code="commonDelivery.sendDate"/></label>
                <div class="input-group date">
                    <form:input type="text" path="newCommonDelivery.sendDate" class="form-control input-sm" size="7" id="newSendDate"/>
                </div>
            </div>
            <div class="form-group">
                <label for="newDeliveryFee"><spring:message code="commonDelivery.deliveryFee"/></label>
                <div class="input-group">
                <form:input type="text" path="newCommonDelivery.deliveryFee" class="form-control input-sm" id="newDeliveryFee"  oninput="javascript:onlyNumber(this)" size="6"  aria-describedby="newDeliveryFeeAddon" value="0"/>
                    <span class="input-group-addon" id="newDeliveryFeeAddon">&yen;</span>
                </div>
                </div>
            <div class="form-group">
                <label for="newTaxRefund"><spring:message code="commonDelivery.taxRefundRate"/></label>
                <div class="input-group">
                <form:input type="text" path="newCommonDelivery.taxRefundRate" class="form-control input-sm"  id="newTaxRefund" size="4" maxlength="6" oninput="javascript:onlyNumber(this)" aria-describedby="newTaxRefundAddon" readonly="true" tabindex="-1" value="16.667" />
                <span class="input-group-addon" id="newTaxRefundAddon">%</span>
            </div>
            </div>
            <div class="form-group">
                <label for="newTariff"><spring:message code="commonDelivery.tariffRate"/></label>
                <div class="input-group">
                    <form:input type="text" path="newCommonDelivery.tariffRate" class="form-control input-sm"  id="newTariff" size="1" oninput="javascript:onlyNumber(this)" aria-describedby="newTariffAddon" readonly="true" tabindex="-1" value="2"/>
                    <span class="input-group-addon" id="newTariffAddon">%</span>
                </div>
            </div>
            <div class="form-group">
                <label for="newStatus"><spring:message code="commonDelivery.status"/></label>
                <form:select path="newCommonDelivery.status" id="newStatus" class="input-sm form-control" >
                    <c:forEach items="${createCommonDeliveryForm.commonDeliveryStatusSet}" var="deliveryStatus">
                        <form:option value="${deliveryStatus}"><spring:message code="commonDelivery.stauts.${deliveryStatus.value}" /></form:option>
                    </c:forEach>
                </form:select>
            </div>
            <input type="button" class="btn btn-sm btn-primary" value="<spring:message code="global.submit"/>" onclick="javascript:createDelivery()"/>
        </div>
    </div>
    <div class="panel panel-primary" id="orderListPanel">
        <div class="panel-heading">
            <spring:message code="createCommonDelivery.panel.orderList" />
        </div>
        <div class="panel-body">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <spring:message code="global.filter" />
                </div>
                <div class="panel-body form-inline">
                    <div class="form-group">
                        <label for="filterPaymentStatus"><spring:message code="order.paymentStatus"/></label>
                        <form:select path="filterPaymentStatus" id="filterPaymentStatus" class="input-sm form-control" >
                                <form:option value="" ><spring:message code="global.all"/></form:option>
                            <c:forEach items="${createCommonDeliveryForm.paymentStatusList}" var="paymentStatus">
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
                            <form:options items="${createCommonDeliveryForm.sellerList}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group pull-right">
                        <input type="button"  class="btn btn-primary btn-sm" value="<spring:message code="global.reset" />" onclick="javascript:resetFilters()"/>
                        <input type="button" class="btn btn-primary btn-sm" value="<spring:message code="global.filter"/>" onclick="javascript:submitForm('filter')" />
                    </div>

                </div>
            </div>
            <input type="button"  class="btn btn-primary btn-sm" value="<spring:message code="global.selectAll" />" onclick="javascript:selectAllOrders()"/>
            <input type="button"  class="btn btn-primary btn-sm" value="<spring:message code="global.reset" />" onclick="javascript:resetSelectedOrders()"/>
            <table id="orderTable" class="table"  width="100%">
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
                <c:forEach items="${createCommonDeliveryForm.availableOrderList}" var="order" varStatus="loop">
                    <tr onclick="javascript:selectOrder(this,${order.id})">
                        <input type="hidden" id="orderId_${loop.index}" value="${order.id}">
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
                        <td>
                            <fmt:formatDate value="${order.createDate}" pattern="${datePattern}"  />
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</form:form>

