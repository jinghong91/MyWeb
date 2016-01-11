<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <script type="text/javascript">
        $(document).ready(function(){
            $("#clientTable").dataTable();
        });

        function rowClick(row){
            $(".selected").toggleClass('selected');
            $(row).toggleClass('selected');
            $("#selectedClientId").val(row.id);
            $("#addressTable").show();
            getAddressViaAjax(row.id);
        }

        function getAddressViaAjax(clientId){
            $.ajax({
                type : "POST",
                url : "<c:url value='/ajax/client/getAddressList?clientId=' />"+clientId,
                timeout : 100000,
                success : function(data) {
                    $('#addressTable tbody tr[id^=addressRowData_]').remove();
                    $('#addressTable tbody tr[id^=addressRowEdit_]').remove();
                    $.each(data, function (index, address) {
                        //for data display
                        $('#addressTable tbody').prepend(
                                "<tr id='addressRowData_"+address.id+"'>" +
                                "<td id='tdReceiver_"+address.id+"'>" + address.receiver + "</td>" +
                                "<td id='tdAddress_"+address.id+"'>" + address.address + "</td>" +
                                "<td id='tdPhone_"+address.id+"'>" + address.phoneNumber + "</td>" +
                                "<td><img src='<c:url value='/resources/image/delete_icon.png' />' class='img-icon' alt='delete' onclick='javascript:deleteAddress("+address.id+")'/></td>" +
                                "<td><img src=' <c:url value='/resources/image/edit_icon.png' /> ' class='img-icon' alt='edit' onclick='javascript:editAddress("+address.id+")'/></td>" +
                                "</tr>")
                        //for data edit
                        $('#addressTable tbody').prepend(
                                "<tr id='addressRowEdit_"+address.id+"' hidden >" +
                                "<td><input type='text' class='input-sm' id='editReceiver_"+address.id+"' placeholder='"+address.receiver+"'/></td>" +
                                "<td><input type='text' class='input-sm' id='editAddress_"+address.id+"' placeholder='"+address.address+"'/></td>" +
                                "<td><input type='text' class='input-sm' id='editPhone_" + address.id + "' placeholder='"+ address.phoneNumber +"'/></td>" +
                                "<td><img src=' <c:url value='/resources/image/confirm_icon.png' />'  class='img-icon' alt='confirm' onclick='javascript:updateAddress("+address.id+")'/></td>" +
                                "<td><img src=' <c:url value='/resources/image/cancel_icon.png' />'   class='img-icon' alt='cancel' onclick='javascript:cancelUpdateAddress("+address.id+")'/></td>" +
                                "</tr>")
                    });
                }
            });
        }

        function deleteAddress(addressId){
            $.ajax({
                type : "POST",
                url : "<c:url value='/ajax/client/deleteAddress?addressId=' />"+addressId,
                timeout : 100000,
                success : function() {
                    getAddressViaAjax($("#selectedClientId").val());
                }
            });
        }
        function editAddress(id){
            //reset input value
            $("#editReceiver_"+id).val($("#tdReceiver_"+id).html());
            $("#editAddress_"+id).val($("#tdAddress_"+id).html());
            $("#editPhone_"+id).val($("#tdPhone_"+id).html());

            $("#addressRowData_"+id).hide();
            $("#addressRowEdit_"+id).show();
        }

        function updateAddress(id){
            var selectedClientId=$("#selectedClientId").val();
            var address = {}
            address["id"]=id;
            address["receiver"] = $("#editReceiver_"+id).val();
            address["address"] = $("#editAddress_"+id).val();
            address["phoneNumber"]=$("#editPhone_"+id).val();

            saveOrUpdateAddressViaAjax(address,selectedClientId);

            $("#addressRowData_"+id).show();
            $("#addressRowEdit_"+id).hide();
        }

        function cancelUpdateAddress(id){
            $("#addressRowData_"+id).show();
            $("#addressRowEdit_"+id).hide();
        }

        function addAddress(){
            $("#addressRow_new").show();
            $("#addressRow_addIcon").hide();
        }

        function confirmAddAddress(){
            var selectedClientId=$("#selectedClientId").val();
            var newAddress = {}
            newAddress["receiver"] = $("#newReceiver").val();
            newAddress["address"] = $("#newAddress").val();
            newAddress["phoneNumber"]=$("#newPhone").val();

            saveOrUpdateAddressViaAjax(newAddress,selectedClientId);

            $("#addressRow_new").hide();
            $("#addressRow_addIcon").show();

            $("#newReceiver").val("");
            $("#newAddress").val("");
            $("#newPhone").val("");
        }

        function cancelAddAddress(){
            $("#addressRow_new").hide();
            $("#addressRow_addIcon").show();
        }

        function saveOrUpdateAddressViaAjax(address,clientId){
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "<c:url value='/ajax/client/saveOrUpdateAddress?clientId=' />"+clientId,
                timeout : 100000,
                data : JSON.stringify(address),
                success : function() {
                    getAddressViaAjax(clientId);
                }
            });
        }
    </script>

<div class="panel panel-primary">
    <div class="panel-heading"></div>
    <div class="panel-body">
        <table class="table" id="clientTable" >
            <thead>
            <tr>
                <th><spring:message code="client.name"/></th>
                <th><spring:message code="client.consumptionAmount"/></th>
                <th><spring:message code="client.consumptionNumber"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${clientList}" var="client">
                <tr id="${client.id}" onclick="javascript:rowClick(this)">
                    <td>${client.name}</td>
                    <td>${client.consumptionNumber}</td>
                    <td>${client.consumptionAmount}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div>
            <input type="hidden" value="0" id="selectedClientId">
            <table class="table" id="addressTable" hidden>
                <thead>
                <tr>
                    <th><spring:message code="address.receiver"/></th>
                    <th><spring:message code="address.address"/></th>
                    <th><spring:message code="address.phone"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr id="addressRow_new" hidden>
                    <td><input type="text" class="input-sm" id="newReceiver"></td>
                    <td><input type="text" class="input-sm" id="newAddress"></td>
                    <td><input type="text" class="input-sm" id="newPhone"></td>
                    <td><img src="<c:url value='/resources/image/confirm_icon.png' />" class="img-icon" alt="confirm" onclick="javascript:confirmAddAddress()"></td>
                    <td><img src="<c:url value='/resources/image/cancel_icon.png' />" class="img-icon" alt="cancel" onclick="javascript:cancelAddAddress()"></td>
                </tr>
                <tr id="addressRow_addIcon">
                    <td colspan="4"></td>
                    <td><img src="<c:url value='/resources/image/add_icon.png' />" class="img-icon" alt="add" onclick="javascript:addAddress()"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

