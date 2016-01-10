package web.form;

import web.model.Client;
import web.model.OrderItem;
import web.model.Seller;

import java.util.ArrayList;
import java.util.List;

public class OrderManagementForm {
   private OrderItem selectedOrder=new OrderItem();

    private List<Client> clientList;

    private List<Seller> sellerList = new ArrayList<Seller>();

    private List<OrderItem> orderList=new ArrayList<OrderItem>();

    public OrderItem getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(OrderItem selectedOrder) {
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

    public List<OrderItem> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderItem> orderList) {
        this.orderList = orderList;
    }
}
