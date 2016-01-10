package web.controller.Ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.model.OrderItem;
import web.service.IOrderItemService;

@RestController
@RequestMapping(value = "/ajax/order")
public class AjaxOrderController {
    @Autowired
    IOrderItemService orderItemService;
    @RequestMapping(value = "/getOrder")
    public OrderItem getOrder(@RequestParam int id){
        return orderItemService.getOrderById(id);
    }
}
