package web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import web.form.OrderManagementForm;
import web.model.Client;
import web.model.Order;
import web.model.Seller;
import web.service.IClientService;
import web.service.IOrderService;
import web.service.impl.ISellerService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/orderManagement")
public class OrderManagementController {
    private static final Logger logger = Logger.getLogger(WelcomeController.class);
    @Autowired
    private IOrderService orderService;
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
        Order newOrder = orderManagementForm.getSelectedOrder();
        Order oldOrder = orderService.getOrderById(newOrder.getId());
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
        orderService.updateOrder(newOrder);

        return "redirect:/orderManagement";
    }

    @RequestMapping("filter")
    public String filter(OrderManagementForm form, Model model){
        form.setOrderList(orderService.getOrderWithoutCommonDeliveryWithFilter(
                form.getFilterPaymentStatus(),
                form.getFilterCreateDateFrom(),
                form.getFilterCreateDateTo(),
                form.getFilterSellerId(),
                form.getFilterClientId()
        ));

        return "orderManagement";
    }

    @ModelAttribute(value = "orderManagementForm")
    public OrderManagementForm initAddOrderForm() {
        OrderManagementForm form = new OrderManagementForm();
        form.setClientList(clientService.getAll());
        form.setSellerList(sellerService.getAll());
        form.setSelectedOrder(new Order());
        form.setOrderList(orderService.getAll());
        return form;
    }

    @ModelAttribute("pageTitle")
    public String  initPageTitle(){
        return "pageTitle.orderManagement";
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


