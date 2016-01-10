package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import web.Utils.MoneyUtils;
import web.form.CreateOrderForm;
import web.model.Client;
import web.model.OrderItem;
import web.model.PersonalDelivery;
import web.model.Seller;
import web.service.IClientService;
import web.service.IOrderItemService;
import web.service.impl.ISellerService;

import java.util.*;

@Controller
@RequestMapping(value = "/createOrder")
@SessionAttributes("addedOrderItemMap")
public class CreateOrdersController {
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private ISellerService sellerService;

    private static int index = 0;

    @RequestMapping(method = RequestMethod.GET)
    public String initCreateOrder(CreateOrderForm createOrderForm, @ModelAttribute("addedOrderItemMap") Map<Integer, OrderItem> addedOrderItemMap, Model model) {
        model.addAttribute("createOrderForm", createOrderForm);
        return "createOrder";
    }

    @RequestMapping(value = "/add")
    public String addOrder(CreateOrderForm createOrderForm, @ModelAttribute("addedOrderItemMap") Map<Integer, OrderItem> addedOrderItemMap, Model model) {
        Client client;
        if (createOrderForm.isNewClient()) {
            client = new Client(createOrderForm.getNewClientName());
            clientService.addClient(client);
            client = clientService.getClientByName(client.getName());
        } else {
            client = clientService.getClientById(createOrderForm.getExistedClient());
        }

        for (OrderItem orderItem : createOrderForm.getNewOrderItemList()) {
            if ("" != orderItem.getName()) {
                orderItem.setClient(client);
                orderItem.setCreateDate(new Date());
                addedOrderItemMap.put(index, orderItem);
                index++;
            }
        }
        return "redirect:/createOrder";
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute("addedOrderItemMap") Map<Integer, OrderItem> addedOrderItemMap, SessionStatus sessionStatus) {

        sessionStatus.setComplete();
        index = 0;
        for (Map.Entry<Integer, OrderItem> entry : addedOrderItemMap.entrySet()) {
            OrderItem order = entry.getValue();
            Client client = order.getClient();
            client.setConsumptionNumber(client.getConsumptionNumber() + 1);
            client.setConsumptionAmount(client.getConsumptionAmount().add(order.getSellPrice()));
            order.setPersonalDelivery(new PersonalDelivery());
            orderItemService.addOrderItem(order);

        }
        return "redirect:/orderManagement";
    }

    @RequestMapping(value = "/delete")
    public String delete(@ModelAttribute("addedOrderItemMap") Map<Integer, OrderItem> addedOrderItemMap, @RequestParam("deletedOrders") String deletedOrder, Model model) {
        String[] deletedOrders = deletedOrder.split(",");
        for (String key : deletedOrders) {
            addedOrderItemMap.remove(Integer.parseInt(key));
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

    @ModelAttribute("addedOrderItemMap")
    public Map<Integer, OrderItem> initAddedOrderItemList() {
        return new HashMap<Integer, OrderItem>();
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
