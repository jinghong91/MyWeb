package web.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CLIENT")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CLIENT")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONSUMPTION_AMOUNT")
    private BigDecimal consumptionAmount;

    @Column(name = "CONSUMPTION_NUMBER")
    private int consumptionNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Address> addressList = new ArrayList<Address>();

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

    public BigDecimal getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(BigDecimal consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }

    public int getConsumptionNumber() {
        return consumptionNumber;
    }

    public void setConsumptionNumber(int consumptionNumber) {
        this.consumptionNumber = consumptionNumber;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
