package web.form;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class DashboardForm {
private Map<String,BigDecimal[]> chartDataMap=new HashMap<String, BigDecimal[]>();
    private int periodType = Calendar.WEEK_OF_YEAR;
    private String chartType = "Bar";
    public Map<String, BigDecimal[]> getChartDataMap() {
        return chartDataMap;
    }

    public void setChartDataMap(Map<String, BigDecimal[]> chartDataMap) {
        this.chartDataMap = chartDataMap;
    }

    public int getPeriodType() {
        return periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
}
