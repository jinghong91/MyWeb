package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.Utils.MoneyUtils;
import web.form.addOrderForm;
import web.model.Client;
import web.model.OrderItem;
import web.model.Seller;
import web.service.IClientService;
import web.service.IOrderItemService;
import web.service.impl.ISellerService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private ISellerService sellerService;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String initOrder(Model model) {
        model.addAttribute("orderItemList", orderItemService.getAll());

        return "order";
    }

    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public String createOrder(addOrderForm newOrderForm, BindingResult result, Model model) {
        Client client;
        if (newOrderForm.isNewClient()) {
            client = new Client(newOrderForm.getNewClientName());
        } else {
            client = clientService.getClientById(newOrderForm.getExistedClient());
        }

        for (OrderItem orderItem : newOrderForm.getNewOrderItemList()) {
            orderItem.setClient(client);
            orderItem.setCreateDate(new Date());
            orderItemService.addOrderItem(orderItem);
        }
        return "redirect:order";
    }

    @ModelAttribute(value = "newOrderForm")
    public addOrderForm initAddOrderForm() {
        addOrderForm form = new addOrderForm();
        form.setClientList(clientService.getAll());
        form.setCurrency(MoneyUtils.getCurrency("EUR", "CNY"));
        form.setSellerList(sellerService.getAll());
        return form;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(List.class, "newOrderItemList.sellerList", new CustomCollectionEditor(List.class) {
            protected Object convertElement(Object element) {
                if (element instanceof Seller) {
                    return element;
                } else if (element instanceof String) {
                    return sellerService.getById(Integer.valueOf(element.toString()));
                }
                return null;
            }
        });
    }

}
