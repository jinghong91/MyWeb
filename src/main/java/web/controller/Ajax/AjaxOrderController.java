package web.controller.Ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.model.Order;
import web.service.IOrderService;

@RestController
@RequestMapping(value = "/ajax/order")
public class AjaxOrderController {
    @Autowired
    IOrderService orderService;
    @RequestMapping(value = "/getOrder")
    public Order getOrder(@RequestParam int id){
        return orderService.getOrderById(id);
    }
}
