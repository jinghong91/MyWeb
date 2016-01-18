package web.service;

import web.model.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IOrderService {
    List<Order> getAll();

    void addOrder(Order order);

    Order getOrderById(int id);

    void updateOrder(Order order);

    List<Order> getOrderWithoutCommonDeliveryList();

    List<Order> getOrderWithoutCommonDeliveryWithFilter(String paymentStatus, Date createDateFrom, Date createDateTo, int sellerId);

    List<Order> getOrderWithoutCommonDeliveryWithFilter(String paymentStatus, Date createDateFrom, Date createDateTo, int sellerId,int clientId);

    BigDecimal calculateOrderProfit(Order order,int orderListSize);

    List<Order> getOrderWithCommonDeliverySortByCreateDate();

}
