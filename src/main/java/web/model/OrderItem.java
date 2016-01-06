package web.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ORDER_ITEM")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ORIGIN_PRICE_EURO")
    private BigDecimal originPriceEuro;

    @Column(name = "ORIGIN_PRICE_CNY")
    private BigDecimal originPriceCNY;

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

    @Column(name = "SELL_WITH_YU")
    private boolean sellWithYu;

    @Column(name = "DELIVERY_TYPE")
    private int deliveryType;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "COMMON_DELIVERY_ID")
    private CommonDelivery commonDelivery;

    @ManyToOne
    @JoinColumn(name = "PERSONAL_DELIVERY_ID")
    private PersonalDelivery personalDelivery;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getOriginPriceCNY() {
        return originPriceCNY;
    }

    public void setOriginPriceCNY(BigDecimal originPriceCNY) {
        this.originPriceCNY = originPriceCNY;
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

    public boolean isSellWithYu() {
        return sellWithYu;
    }

    public void setSellWithYu(boolean sellWithYu) {
        this.sellWithYu = sellWithYu;
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

  /*  public BigDecimal getTotalProfit() {
        if (this.status >= 4) {
            BigDecimal fee = new BigDecimal(0);
            switch (this.deliveryType) {
                case 1:
                    this.commonDelivery.getDeliveryFee().add();
            }

            (this.sellPrice.
                    subtract(this.originPriceCNY).
                    subtract(this.commonDelivery.getDeliveryFee())).
                    divide(isSellWithYu() ? new BigDecimal(2) : new BigDecimal(1));
        } else {
            return new BigDecimal(0);
        }
    }

    public BigDecimal getImportFee() {
        if (this.deliveryType == 1) {
            return this.getOriginPriceCNY().multiply(new BigDecimal(0.02));
        }
        return new BigDecimal(0);
    }*/

}
