package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.service.IOrderItemService;

@Controller
public class OrderController {
    @Autowired
    private IOrderItemService orderItemService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String initOrder(Model model) {
        model.addAttribute("orderItemList", orderItemService.getAll());
        return "order";
    }

}
