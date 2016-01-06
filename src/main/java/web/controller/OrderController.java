package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.form.addOrderForm;
import web.model.OrderItem;
import web.service.IClientService;
import web.service.IOrderItemService;

import java.util.ArrayList;

@Controller
public class OrderController {
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IClientService clientService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String initOrder(Model model) {
        model.addAttribute("orderItemList", orderItemService.getAll());

        return "order";
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public String createOrder(addOrderForm newOrderForm, BindingResult result, Model model) {
        int i = 0;
        return "redirect:order";
    }

    @ModelAttribute(value = "newOrderForm")
    public addOrderForm initAddOrderForm() {
        addOrderForm form = new addOrderForm();
        form.setClientList(clientService.getAll());
        return form;
    }

}
