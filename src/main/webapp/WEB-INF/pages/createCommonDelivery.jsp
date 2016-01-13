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
            var error="";
            $("#error").empty();
            if(  $("#newTaxRefund").val()>100){
                $("#newTaxRefund").addClass("input-error");
                <spring:message code="commonDelivery.taxRefundRate" var="labeLTaxRefundRate"/>
             error+= "<p><spring:message code="global.error.invalid" arguments="${labeLTaxRefundRate}"/></p>";
            }
            if(!$.isNumeric($("#newTariff").val())){
                $("#newTariff").addClass("input-error");
                <spring:message code="commonDelivery.tariff" var="labelTariff"/>
                error+= "<p><spring:message code="global.error.invalid" arguments="${labelTariff}"/></p>";
            }if(!$.isNumeric($("#newDeliveryFee").val())){
                $("#newDeliveryFee").addClass("input-error");
                <spring:message code="commonDelivery.taxRefundRate" var="labelDeliveryFee"/>
                error+= "<p><spring:message code="global.error.invalid" arguments="${labelDeliveryFee}"/></p>";
            }

            if(error==""){
            var selectedOrders=""
            $("#orderTable tbody tr.selected").each(function(){
                selectedOrders+="_"+$(this).find("[id^=orderId]").val();
            });

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
            if($("#newType").val()=="shopper"){
                $("#newTaxRefund").val("16.667");
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
                    <c:forEach items="${createCommonDeliveryForm.commonDeliveryTypeList}" var="deliveryType">
                        <form:option value="${deliveryType}"><spring:message code="commonDelivery.type.${deliveryType}" /></form:option>
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
                <form:input type="text" path="newCommonDelivery.deliveryFee" class="form-control input-sm" id="newDeliveryFee"  oninput="javascript:onlyNumber(this)" size="6"  aria-describedby="newDeliveryFeeAddon"/>
                    <span class="input-group-addon" id="newDeliveryFeeAddon">&yen;</span>
                </div>
                </div>
            <div class="form-group">
                <label for="newTaxRefund"><spring:message code="commonDelivery.taxRefundRate"/></label>
                <div class="input-group">
                <form:input type="text" path="newCommonDelivery.taxRefundRate" class="form-control input-sm"  id="newTaxRefund" size="4" maxlength="6" oninput="javascript:onlyNumber(this)" aria-describedby="newTaxRefundAddon" value="16.667" />
                <span class="input-group-addon" id="newTaxRefundAddon">%</span>
            </div>
            </div>
            <div class="form-group">
                <label for="newTariff"><spring:message code="commonDelivery.tariff"/></label>
                <div class="input-group">
                    <form:input type="text" path="newCommonDelivery.tariff" class="form-control input-sm"  id="newTariff" size="6" oninput="javascript:onlyNumber(this)" aria-describedby="newTariffAddon"/>
                    <span class="input-group-addon" id="newTariffAddon">&yen;</span>
                </div>
            </div>
            <div class="form-group">
                <label for="newStatus"><spring:message code="commonDelivery.status"/></label>
                <form:select path="newCommonDelivery.status" id="newStatus" class="input-sm form-control" >
                    <c:forEach items="${createCommonDeliveryForm.commonDeliveryStatusList}" var="deliveryStatus">
                        <form:option value="${deliveryStatus}"><spring:message code="commonDelivery.stauts.${deliveryStatus}" /></form:option>
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

