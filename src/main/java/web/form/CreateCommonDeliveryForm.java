package web.form;


import web.model.CommonDelivery;
import web.model.Order;
import web.model.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateCommonDeliveryForm {
    private CommonDelivery newCommonDelivery;

    private List<Order>  availableOrderList=new ArrayList<Order>();

    private String filterPaymentStatus;

    private Date filterCreateDateFrom;

    private Date filterCreateDateTo;

    private int filterSellerId;

    private List<Seller> sellerList;

    public CommonDelivery getNewCommonDelivery() {
        return newCommonDelivery;
    }

    public void setNewCommonDelivery(CommonDelivery newCommonDelivery) {
        this.newCommonDelivery = newCommonDelivery;
    }

    public List<Order> getAvailableOrderList() {
        return availableOrderList;
    }

    public void setAvailableOrderList(List<Order> availableOrderList) {
        this.availableOrderList = availableOrderList;
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

    public int getFilterSellerId() {
        return filterSellerId;
    }

    public void setFilterSellerId(int filterSellerId) {
        this.filterSellerId = filterSellerId;
    }

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<Seller> sellerList) {
        this.sellerList = sellerList;
    }
}
