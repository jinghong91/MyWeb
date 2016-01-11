package web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SELLER")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_SELLER")
    private Integer id;

    @Column(name = "NAME")
    private String name;
    @ManyToMany(mappedBy = "sellerList",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orderList =new ArrayList<Order>();

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

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
            this.orderList = orderList;
    }
}
