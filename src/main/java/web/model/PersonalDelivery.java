package web.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "PERSONAL_DELIVERY")
public class PersonalDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PERSONAL_DELIVERY")
    private Integer id;

    @Column(name = "REF_PACKAGE")
    private String refPackage;

    @Column(name = "STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @OneToOne(mappedBy = "personalDelivery")
    @JsonIgnore
    private Order order;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
