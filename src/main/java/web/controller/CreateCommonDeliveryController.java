package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.Utils.CommonDeliveryType;
import web.form.CreateCommonDeliveryForm;
import web.model.CommonDelivery;
import web.model.Order;
import web.service.ICommonDeliveryService;
import web.service.IOrderService;
import web.service.impl.ISellerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/createCommonDelivery")
public class CreateCommonDeliveryController {
    @Autowired
    ICommonDeliveryService commonDeliveryService;

    @Autowired
    ISellerService sellerService;

    @Autowired
    IOrderService orderService;

    @RequestMapping()
    public String init(Model model) {

        return "createCommonDelivery";
    }

    @RequestMapping(value = "/create")
    public String create(CreateCommonDeliveryForm form, @RequestParam String selectedOrders, Model model) {
        String[] orderIds = selectedOrders.split("_");
        CommonDelivery newCommonDelivery = form.getNewCommonDelivery();
        List<Order> orderList = new ArrayList<Order>();
        for (String orderId : orderIds) {
            if (!orderId.equals("")) {
                Order order=orderService.getOrderById(Integer.valueOf(orderId));
                order.setCommonDelivery(newCommonDelivery);
                order.setProfit(orderService.calculateOrderProfit(order,orderIds.length));
                orderList.add(order);
            }
        }
        newCommonDelivery.setOrderList(orderList);
        commonDeliveryService.addCommonDelivery(newCommonDelivery);
        return "redirect:/createCommonDelivery";
    }

    @RequestMapping(value = "/filter")
    public String filter(CreateCommonDeliveryForm form, Model model) {
        form.setAvailableOrderList(orderService.getOrderWithoutCommonDeliveryWithFilter(
                form.getFilterPaymentStatus(),
                form.getFilterCreateDateFrom(),
                form.getFilterCreateDateTo(),
                form.getFilterSellerId()
        ));

        return "createCommonDelivery";
    }

    @ModelAttribute("createCommonDeliveryForm")
    public CreateCommonDeliveryForm initForm() {
        CreateCommonDeliveryForm form = new CreateCommonDeliveryForm();
        form.setNewCommonDelivery(new CommonDelivery());
        form.setAvailableOrderList(orderService.getOrderWithoutCommonDeliveryList());
        form.setSellerList(sellerService.getAll());
        return form;
    }

    @ModelAttribute("pageTitle")
    public String initPageTitle() {
        return "pageTitle.createCommonDelivery";
    }

}
