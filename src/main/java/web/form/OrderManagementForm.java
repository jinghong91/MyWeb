package web.form;

import web.Utils.PaymentStatus;
import web.model.Client;
import web.model.Order;
import web.model.Seller;

import java.util.ArrayList;
import java.util.List;

public class OrderManagementForm {
   private Order selectedOrder=new Order();

    private List<Client> clientList;

    private List<Seller> sellerList = new ArrayList<Seller>();

    private List<Order> orderList=new ArrayList<Order>();

    private   String[] paymentStatusList = PaymentStatus.getPaymentStatusList();

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
}
