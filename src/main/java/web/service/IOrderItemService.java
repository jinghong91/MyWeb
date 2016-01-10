package web.service;

import web.model.OrderItem;

import java.util.List;

public interface IOrderItemService {
    List<OrderItem> getAll();

    void addOrderItem(OrderItem orderItem);

    OrderItem getOrderById(int id);

    void updateOrder(OrderItem orderItem);
}
