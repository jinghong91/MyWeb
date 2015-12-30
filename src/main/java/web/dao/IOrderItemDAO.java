package web.dao;

import web.model.OrderItem;

import java.util.List;

public interface IOrderItemDAO {
    List<OrderItem> getAll();

    void addOrderItem(OrderItem orderItem);
}
