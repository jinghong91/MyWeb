package web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.Utils.MoneyUtils;
import web.form.CreateOrderForm;
import web.form.OrderManagementForm;
import web.model.Client;
import web.model.OrderItem;
import web.model.Seller;
import web.service.IClientService;
import web.service.IOrderItemService;
import web.service.impl.ISellerService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/orderManagement")
public class OrderManagementController {
    private static final Logger logger = Logger.getLogger(WelcomeController.class);
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private ISellerService sellerService;

    @RequestMapping()
    public String init(Model model) {
        return "orderManagement";
    }

    @RequestMapping(value = "/update")
    public String update(OrderManagementForm orderManagementForm, Model model) {
        OrderItem newOrder = orderManagementForm.getSelectedOrder();
        OrderItem oldOrder = orderItemService.getOrderById(newOrder.getId());
        Client newClient = clientService.getClientById(newOrder.getClient().getId());
        Client oldClient = oldOrder.getClient();

        //update consumption
        if (!newOrder.getClient().getId().equals(oldClient.getId() )) {
            oldClient.setConsumptionNumber(oldClient.getConsumptionNumber() - 1);
            oldClient.setConsumptionAmount(oldClient.getConsumptionAmount().subtract(oldOrder.getSellPrice()));
            clientService.updateClient(oldClient);
            newClient.setConsumptionNumber(newClient.getConsumptionNumber() + 1);
            newClient.setConsumptionAmount(newClient.getConsumptionAmount().add(newOrder.getSellPrice()));
        }else if ( !newOrder.getSellPrice().equals(oldOrder.getSellPrice())){
            newClient.setConsumptionAmount(
                    newClient.getConsumptionAmount().
                            subtract(oldOrder.getSellPrice()).
                            add(newOrder.getSellPrice()));
        }

        if(newOrder.getCommonDelivery().getId()==null){
            newOrder.setCommonDelivery(null);
        }

        newOrder.setClient(newClient);
        orderItemService.updateOrder(newOrder);

        return "redirect:/orderManagement";
    }

    @ModelAttribute(value = "orderManagementForm")
    public OrderManagementForm initAddOrderForm() {
        OrderManagementForm form = new OrderManagementForm();
        form.setClientList(clientService.getAll());
        form.setSellerList(sellerService.getAll());
        form.setSelectedOrder(new OrderItem());
        form.setOrderList(orderItemService.getAll());
        return form;
    }

    @InitBinder("orderManagementForm")
    protected void initSellerListBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(List.class, "selectedOrder.sellerList", new CustomCollectionEditor(List.class) {
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


