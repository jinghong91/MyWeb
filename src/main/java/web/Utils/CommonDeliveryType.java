package web.Utils;

public enum CommonDeliveryType {
    SHOPPER("shopper"), GUIDE("guide"), POST("post"), OTHER("other");

    private String value;
    private static final CommonDeliveryType[] commonDeliveryTypeList = {SHOPPER, GUIDE, POST, OTHER};

    private CommonDeliveryType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String getValue(){
        return value;
    }

    public static CommonDeliveryType[] getCommonDeliveryTypeList() {
        return commonDeliveryTypeList;
    }
}
