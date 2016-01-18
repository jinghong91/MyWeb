package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import web.form.DashboardForm;
import web.model.Order;
import web.service.IOrderService;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {
    @Autowired
    IOrderService orderService;

    @RequestMapping(value = "/changeType")
    public String changeType(DashboardForm dashboardForm, Model model) {

        return "dashboard";
    }

    @RequestMapping()
    public String init(DashboardForm dashboardForm, Model model) {

        return "dashboard";
    }


    @ModelAttribute("dashboardForm")
    public DashboardForm initDashboardForm(DashboardForm form, Model model) {
        if (form == null) {
            form = new DashboardForm();
        }
        int periodType = form.getPeriodType();
        List<Order> orderList = orderService.getOrderWithCommonDeliverySortByCreateDate();
        Map<String, BigDecimal[]> chartDataMap = new HashMap<String, BigDecimal[]>();
        Date firstDate = orderList.get(0).getCreateDate();
        Date lastDate = orderList.get(orderList.size() - 1).getCreateDate();
        int beginYear = getNumber(firstDate, Calendar.YEAR);
        int endYear = getNumber(lastDate, Calendar.YEAR);
        int begin = getNumber(firstDate, periodType);
        int end = getNumber(lastDate, periodType);
        String format = "";
        switch (periodType) {
            case Calendar.YEAR:
                format = "{year}";
                break;
            case Calendar.MONTH:
                format = "{year}-{month}";
                break;
            case Calendar.WEEK_OF_YEAR:
            default:
                format = "{year} W{week}";
                break;
        }
        for (; beginYear <= endYear; beginYear++) {
            for (; begin <= end; begin++) {
                String period = format.replace("{year}", String.valueOf(beginYear)).replace("{month}", String.valueOf(begin)).replace("{week}", String.valueOf(begin));
                chartDataMap.put(period, new BigDecimal[]{new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)});
            }
        }

        for (Order order : orderList) {
            String date = formatDatePeriod(order.getCreateDate(), periodType);
            BigDecimal[] totals = chartDataMap.get(date);
            totals[0] = totals[0].add(order.getOriginPriceCNY());
            totals[1] = totals[1].add(order.getSellPrice());
            totals[2] = totals[2].add(order.getProfit());

            chartDataMap.put(date, totals);
        }

        form.setChartDataMap(chartDataMap);
        return form;
    }


    @ModelAttribute("pageTitle")
    public String initPageTitle() {
        return "pageTitle.createOrder";
    }

    private int getNumber(Date date, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (type == Calendar.MONTH) {
            return cal.get(type) + 1;
        } else {
            return cal.get(type);
        }
    }

    private String formatDatePeriod(Date date, Integer type) {
        switch (type) {
            case Calendar.YEAR:
                return String.valueOf(getNumber(date, Calendar.YEAR));

            case Calendar.MONTH:
                return getNumber(date, Calendar.YEAR) + "-" + getNumber(date, Calendar.MONTH);

            case Calendar.WEEK_OF_YEAR:
            default:
                return getNumber(date, Calendar.YEAR) + " W" + getNumber(date, Calendar.WEEK_OF_YEAR);
        }
    }
}
