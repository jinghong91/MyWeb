package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AbstractDAO;
import web.dao.IOrderDAO;
import web.model.Order;
import web.service.IOrderService;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService extends AbstractDAO<Order> implements IOrderService {
    @Autowired
    private IOrderDAO orderDAO;

    @Override
    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    @Override
    public void addOrder(Order order) {
        orderDAO.addOrder(order);
    }

    @Override
    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    @Override
    public List<Order> getOrderWithoutCommonDeliveryList() {
        return orderDAO.getOrderWithoutCommonDeliveryList();
    }

    @Override
    public List<Order> getOrderWithoutCommonDeliveryWithFilter(String paymentStatus, Date createDateFrom, Date createDateTo, int sellerId) {
        return orderDAO.getOrderWithoutCommonDeliveryWithFilter(paymentStatus, createDateFrom, createDateTo, sellerId);
    }
}
