package web.dao.impl;

import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IOrderItemDAO;
import web.model.OrderItem;

import java.util.List;

@Repository
public class OrderItemDAO extends AbstractDAO<OrderItem> implements IOrderItemDAO {
    @Override
    public List<OrderItem> getAll() {
        return createEntityCriteria()
                .createAlias("client", "client")
                .createAlias("personalDelivery", "personalDelivery")
                .createAlias("commonDelivery", "commonDelivery")
                .createAlias("address", "address")
                .list();
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        persist(orderItem);
    }
}
