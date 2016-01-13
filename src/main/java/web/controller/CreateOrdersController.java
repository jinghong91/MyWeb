package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import web.Utils.MoneyUtils;
import web.Utils.PaymentStatus;
import web.form.CreateOrderForm;
import web.model.Client;
import web.model.Order;
import web.model.PersonalDelivery;
import web.model.Seller;
import web.service.IClientService;
import web.service.IOrderService;
import web.service.impl.ISellerService;

import java.util.*;

@Controller
@RequestMapping(value = "/createOrder")
@SessionAttributes("addedOrderMap")
public class CreateOrdersController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private ISellerService sellerService;

    private static int index = 0;

    @RequestMapping(method = RequestMethod.GET)
    public String initCreateOrder(CreateOrderForm createOrderForm, @ModelAttribute("addedOrderMap") Map<Integer, Order> addedOrderMap, Model model) {
        model.addAttribute("createOrderForm", createOrderForm);
        return "createOrder";
    }

    @RequestMapping(value = "/add")
    public String addOrder(CreateOrderForm createOrderForm, @ModelAttribute("addedOrderMap") Map<Integer, Order> addedOrderMap, Model model) {
        Client client;
        if (createOrderForm.isNewClient()) {
            client = new Client(createOrderForm.getNewClientName());
            clientService.addClient(client);
            client = clientService.getClientByName(client.getName());
        } else {
            client = clientService.getClientById(createOrderForm.getExistedClient());
        }

        for (Order order : createOrderForm.getNewOrderList()) {
            if ("" != order.getName()) {
                order.setClient(client);
                order.setCreateDate(new Date());
                addedOrderMap.put(index, order);
                index++;
            }
        }
        return "redirect:/createOrder";
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute("addedOrderMap") Map<Integer, Order> addedOrderMap, SessionStatus sessionStatus) {

        sessionStatus.setComplete();
        index = 0;
        for (Map.Entry<Integer, Order> entry : addedOrderMap.entrySet()) {
            Order order = entry.getValue();
            Client client = order.getClient();
            client.setConsumptionNumber(client.getConsumptionNumber() + 1);
            client.setConsumptionAmount(client.getConsumptionAmount().add(order.getSellPrice()));
            order.setPersonalDelivery(new PersonalDelivery());
            orderService.addOrder(order);

        }
        return "redirect:/orderManagement";
    }

    @RequestMapping(value = "/delete")
    public String delete(@ModelAttribute("addedOrderMap") Map<Integer, Order> addedOrderMap, @RequestParam("deletedOrders") String deletedOrder, Model model) {
        String[] deletedOrders = deletedOrder.split(",");
        for (String key : deletedOrders) {
            addedOrderMap.remove(Integer.parseInt(key));
        }
        return "redirect:/createOrder";
    }

    @ModelAttribute(value = "createOrderForm")
    public CreateOrderForm initAddOrderForm() {
        CreateOrderForm form = new CreateOrderForm();

        form.setClientList(clientService.getAll());
        form.setCurrency(MoneyUtils.getCurrency("EUR", "CNY"));
        form.setSellerList(sellerService.getAll());
        return form;
    }

    @ModelAttribute("addedOrderMap")
    public Map<Integer, Order> initAddedOrderList() {
        return new HashMap<Integer, Order>();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(List.class, "newOrderList.sellerList", new CustomCollectionEditor(List.class) {
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

    @ModelAttribute("pageTitle")
    public String  initPageTitle(){
        return "pageTitle.createOrder";
    }
}
