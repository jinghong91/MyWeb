package web.service;

import web.model.OrderItem;

import java.util.List;

public interface IOrderItemService {
    List<OrderItem> getAll();
}
