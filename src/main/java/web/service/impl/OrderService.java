package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Utils.CommonDeliveryType;
import web.dao.AbstractDAO;
import web.dao.IOrderDAO;
import web.model.CommonDelivery;
import web.model.Order;
import web.service.IOrderService;

import java.math.BigDecimal;
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
        return orderDAO.getOrderWithoutCommonDeliveryWithFilter(paymentStatus, createDateFrom, createDateTo, sellerId, 0);
    }

    @Override
    public List<Order> getOrderWithoutCommonDeliveryWithFilter(String paymentStatus, Date createDateFrom, Date createDateTo, int sellerId, int clientId) {
        return orderDAO.getOrderWithoutCommonDeliveryWithFilter(paymentStatus, createDateFrom, createDateTo, sellerId, clientId);
    }

    @Override
    public BigDecimal calculateOrderProfit(Order order,int orderListSize) {
        CommonDelivery commonDelivery = order.getCommonDelivery();

        BigDecimal originPrice = order.getOriginPriceCNY();

        BigDecimal sellPrice = order.getSellPrice();
        int size=commonDelivery.getOrderList().size();
        if(size==0){
            size=orderListSize;
        }
        BigDecimal deliveryFee = commonDelivery.getDeliveryFee().divide(new BigDecimal(size));
        BigDecimal tariff = tariff = originPrice.multiply(commonDelivery.getTariffRate());
        BigDecimal taxRefund = originPrice.multiply(commonDelivery.getTaxRefundRate());

       if(commonDelivery.getType().equals(CommonDeliveryType.SHOPPER.getValue())) {
           taxRefund = originPrice.divide(new BigDecimal(6),2,BigDecimal.ROUND_HALF_UP);
       }
        BigDecimal profit=sellPrice.subtract(originPrice).subtract(deliveryFee).subtract(tariff).add(taxRefund);

        return profit;
    }

    @Override
    public List<Order> getOrderWithCommonDeliverySortByCreateDate() {
        return orderDAO.getOrderWithCommonDeliverySortByCreateDate();
    }
}
