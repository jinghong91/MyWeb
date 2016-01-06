package web.form;

import web.model.Client;
import web.model.OrderItem;
import web.model.Seller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class addOrderForm {
    private boolean newClient;

    private int existedClient;

    private String newClientName;

    private List<Client> clientList;

    private List<OrderItem> newOrderItemList=new ArrayList<OrderItem>();

    private BigDecimal currency;
    private List<Seller> sellerList=new ArrayList<Seller>();

    public boolean isNewClient() {
        return newClient;
    }

    public void setNewClient(boolean newClient) {
        this.newClient = newClient;
    }

    public int getExistedClient() {
        return existedClient;
    }

    public void setExistedClient(int existedClient) {
        this.existedClient = existedClient;
    }

    public String getNewClientName() {
        return newClientName;
    }

    public void setNewClientName(String newClientName) {
        this.newClientName = newClientName;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<OrderItem> getNewOrderItemList() {
        return newOrderItemList;
    }

    public void setNewOrderItemList(List<OrderItem> newOrderItemList) {
        this.newOrderItemList = newOrderItemList;
    }

    public BigDecimal getCurrency() {
        return currency;
    }

    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<Seller> sellerList) {
        this.sellerList = sellerList;
    }
}
