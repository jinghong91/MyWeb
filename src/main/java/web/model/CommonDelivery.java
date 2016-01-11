package web.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "COMMON_DELIVERY")
public class CommonDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_COMMON_DELIVERY")
    private Integer id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SEND_DATE")
    private Date sendDate;

    @Column(name = "DELIVERY_FEE")
    private BigDecimal deliveryFee;

    @Column(name = "TAX_REFUND_RATE")
    private BigDecimal taxRefundRate;

    @Column(name = "TARIFF")
    private BigDecimal tariff;

    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "commonDelivery")
    private List<Order> orderList = new ArrayList<Order>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getTaxRefundRate() {
        return taxRefundRate;
    }

    public void setTaxRefundRate(BigDecimal taxRefundRate) {
        this.taxRefundRate = taxRefundRate;
    }

    public BigDecimal getTariff() {
        return tariff;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}


