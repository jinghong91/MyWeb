package web.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import web.Utils.CommonDeliveryStatus;
import web.Utils.CommonDeliveryType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    @Column(name = "TARIFF_RATE")
    private BigDecimal tariffRate;

    @Column(name = "STATUS")
    private String status;

    @OneToMany(mappedBy = "commonDelivery")
    @Cascade({CascadeType.SAVE_UPDATE,CascadeType.MERGE})
    @JsonIgnore
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



    public BigDecimal getTaxRefundRate() {
        return taxRefundRate;
    }

    public void setTaxRefundRate(BigDecimal taxRefundRate) {
        this.taxRefundRate = taxRefundRate;
    }

    public BigDecimal getTariffRate() {
        return tariffRate;
    }

    public void setTariffRate(BigDecimal tariffRate) {
        this.tariffRate = tariffRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


