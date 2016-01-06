package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AbstractDAO;
import web.dao.IOrderItemDAO;
import web.model.OrderItem;
import web.service.IOrderItemService;

import java.util.List;

@Service
@Transactional
public class OrderItemService extends AbstractDAO<OrderItem> implements IOrderItemService {
    @Autowired
    private IOrderItemDAO orderItemDAO;

    @Override
    public List<OrderItem> getAll() {
        return orderItemDAO.getAll();
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        orderItemDAO.addOrderItem(orderItem);
    }
}
