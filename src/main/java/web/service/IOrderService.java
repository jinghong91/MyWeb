package web.service;

import web.model.Order;

import java.util.Date;
import java.util.List;

public interface IOrderService {
    List<Order> getAll();

    void addOrder(Order order);

    Order getOrderById(int id);

    void updateOrder(Order order);

    List<Order> getOrderWithoutCommonDeliveryList();

    List<Order> getOrderWithoutCommonDeliveryWithFilter(String paymentStatus, Date createDateFrom, Date createDateTo, int sellerId);
}
