package web.form;

import web.model.Client;
import web.model.Order;
import web.model.Seller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderForm {
    private boolean newClient;

    private int existedClient;

    private String newClientName;

    private List<Client> clientList;

    private List<Order> newOrderList = new ArrayList<Order>();

    private BigDecimal currency;

    private List<Seller> sellerList = new ArrayList<Seller>();

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

    public List<Order> getNewOrderList() {
        return newOrderList;
    }

    public void setNewOrderList(List<Order> newOrderList) {
        this.newOrderList = newOrderList;
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
