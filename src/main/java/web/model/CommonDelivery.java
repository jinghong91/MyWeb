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

    @Column(name = "REF_PACKAGE")
    private String refPackage;

    @Column(name = "SEND_DATE")
    private Date sendDate;

    @Column(name = "DELIVERY_FEE")
    private BigDecimal deliveryFee;

    @OneToMany(mappedBy = "commonDelivery", fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefPackage() {
        return refPackage;
    }

    public void setRefPackage(String refPackage) {
        this.refPackage = refPackage;
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

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
