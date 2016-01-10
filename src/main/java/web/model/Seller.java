package web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.AutoPopulatingList;

import javax.persistence.*;
import javax.persistence.criteria.Order;
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
    private List<OrderItem> orderItemList=new ArrayList<OrderItem>();

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

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
            this.orderItemList=orderItemList;
    }
}
