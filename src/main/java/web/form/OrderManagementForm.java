package web.form;

import web.Utils.PaymentStatus;
import web.model.Client;
import web.model.Order;
import web.model.Seller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManagementForm {
   private Order selectedOrder=new Order();

    private List<Client> clientList;

    private List<Seller> sellerList = new ArrayList<Seller>();

    private List<Order> orderList=new ArrayList<Order>();

    private   String[] paymentStatusList = PaymentStatus.getPaymentStatusList();

    private int filterClientId;

    private String filterPaymentStatus;

    private Date filterCreateDateFrom;

    private Date filterCreateDateTo;

    private int filterSellerId;

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<Seller> sellerList) {
        this.sellerList = sellerList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public String[] getPaymentStatusList() {
        return paymentStatusList;
    }

    public String getFilterPaymentStatus() {
        return filterPaymentStatus;
    }

    public void setFilterPaymentStatus(String filterPaymentStatus) {
        this.filterPaymentStatus = filterPaymentStatus;
    }

    public Date getFilterCreateDateFrom() {
        return filterCreateDateFrom;
    }

    public void setFilterCreateDateFrom(Date filterCreateDateFrom) {
        this.filterCreateDateFrom = filterCreateDateFrom;
    }

    public Date getFilterCreateDateTo() {
        return filterCreateDateTo;
    }

    public void setFilterCreateDateTo(Date filterCreateDateTo) {
        this.filterCreateDateTo = filterCreateDateTo;
    }

    public int getFilterClientId() {
        return filterClientId;
    }

    public void setFilterClientId(int filterClientId) {
        this.filterClientId = filterClientId;
    }

    public int getFilterSellerId() {
        return filterSellerId;
    }

    public void setFilterSellerId(int filterSellerId) {
        this.filterSellerId = filterSellerId;
    }

    public BigDecimal getTotalSellAmount(){
        BigDecimal total=new BigDecimal(0);
       for(Order order:orderList){
           total=total.add(order.getSellPrice());
       }
        return total.setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
