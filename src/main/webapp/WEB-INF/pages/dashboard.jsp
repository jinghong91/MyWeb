<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link rel="stylesheet" href="/resources/css/morris.css">
<script src="/resources/js/raphael-min.js"></script>
<script src="/resources/js/morris.min.js"></script>
<script>
    $(document).ready(function(){
        new Morris.${dashboardForm.chartType}({
            // ID of the element in which to draw the chart.
            element: 'myChart',
            // Chart data records -- each entry in this array corresponds to a point on
            // the chart.
            data: prepareData(),
            // The name of the data record attribute that contains x-values.
            xkey: 'period',
            // A list of names of data record attributes that contain y-values.
            ykeys: ['buy','sell','profit'],
            // Labels for the ykeys -- will be displayed when you hover over the
            // chart.
            labels: ['buy','sell','profit']
        });
    });

    function prepareData() {
        var ret = [];
        <c:forEach var="data" items="${dashboardForm.chartDataMap}">
        ret.push({
            period:"${data.key}",
            buy: ${data.value[0]},
            sell: ${data.value[1]},
            profit: ${data.value[2]}
        });
        </c:forEach>

        return ret;
    }

    function changeType(){
        submitForm("changeType");
    }

    function submitForm(method){
        var formAction ="<c:url value="/dashboard"/>" +"/"+method;
        $("#dashBoardForm").attr("action",  formAction);
        $("#dashBoardForm")  .submit();
    }

</script>


<form:form modelAttribute="dashboardForm" action="" id="dashBoardForm">
<div class="panel panel-primary">
    <div class="panel-heading">
        <spring:message code="pageTitle.dashboard" />
    </div>
    <div class="panel-body form-inline" >
            <div class="form-group">
                <form:select path="periodType" cssClass="form-control input-sm">
                    <form:option value="3">WEEK</form:option>
                    <form:option value="2">MONTH</form:option>
                    <form:option value="1">YEAR</form:option>
                </form:select>
            </div>
            <div class="form-group">
            <form:select path="chartType" cssClass="form-control input-sm">
                <form:option value="Line">LINE</form:option>
                <form:option value="Bar">BAR</form:option>
            </form:select>
            </div>
            <div class="form-group">
                <input type="button" class="btn btn-sm btn-primary" value="<spring:message code="global.submit" />" onclick="javascript:changeType()"/>
            </div>
        <div id="myCchart" style="height: 250px;"></div>
    </div>

</div>
</form:form>



