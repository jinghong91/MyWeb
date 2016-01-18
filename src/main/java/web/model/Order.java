package web.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.AutoPopulatingList;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDER_ITEM")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ORDER_ITEM")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ORIGIN_PRICE_EURO")
    private BigDecimal originPriceEuro;

    @Column(name = "BOUGHT_CURRENCY")
    private BigDecimal boughtCurrency;

    @Column(name = "SELL_PRICE")
    private BigDecimal sellPrice;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name = "PAID_AMOUNT")
    private BigDecimal paidAmount;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name="PROFIT")
    private BigDecimal profit;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "COMMON_DELIVERY_ID")
    private CommonDelivery commonDelivery;

    @ManyToOne()
    @JoinColumn(name = "PERSONAL_DELIVERY_ID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private PersonalDelivery personalDelivery;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ORDER_ITEM_SELLER", joinColumns = {
            @JoinColumn(name = "ORDER_ITEM_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SELLER_ID",
                    nullable = false, updatable = false)})
    private List<Seller> sellerList=new ArrayList<Seller>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOriginPriceEuro() {
        return originPriceEuro;
    }

    public void setOriginPriceEuro(BigDecimal originPriceEuro) {
        this.originPriceEuro = originPriceEuro;
    }


    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CommonDelivery getCommonDelivery() {
        return commonDelivery;
    }

    public void setCommonDelivery(CommonDelivery commonDelivery) {
        this.commonDelivery = commonDelivery;
    }

    public PersonalDelivery getPersonalDelivery() {
        return personalDelivery;
    }

    public void setPersonalDelivery(PersonalDelivery personalDelivery) {
        this.personalDelivery = personalDelivery;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getBoughtCurrency() {
        return boughtCurrency;
    }

    public void setBoughtCurrency(BigDecimal boughtCurrency) {
        this.boughtCurrency = boughtCurrency;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<Seller> sellerList) {
        this.sellerList = sellerList;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getOriginPriceCNY() {
        return this.originPriceEuro.multiply(this.boughtCurrency).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

}
