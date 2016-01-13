package web.Utils;

/**
 * Created by Administrator on 2016/1/12.
 */
public enum CommonDeliveryType {
    SHOPPER("shopper"), GUIDE("guide"), POST("post"), OTHER("other");

    private String value;
    private static final String[] commonDeliveryTypeList = {SHOPPER.value, GUIDE.value, POST.value, OTHER.value};

    private CommonDeliveryType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static String[] getCommonDeliveryTypeList() {
        return commonDeliveryTypeList;
    }
}
