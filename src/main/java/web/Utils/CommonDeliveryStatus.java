package web.Utils;

/**
 * Created by Administrator on 2016/1/12.
 */
public enum CommonDeliveryStatus {
    DELIVERING("delivering"), COMPLETE("complete");
    private String value;
    private static final String[] commonDeliveryStatusList = {DELIVERING.value, COMPLETE.value};

    private CommonDeliveryStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static String[] getCommonDeliveryStatusList() {
        return commonDeliveryStatusList;
    }

}
