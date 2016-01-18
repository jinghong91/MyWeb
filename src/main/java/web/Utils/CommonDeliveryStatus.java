package web.Utils;

public enum CommonDeliveryStatus {
    DELIVERING("delivering"), COMPLETE("complete");

    private String value;
    private static final CommonDeliveryStatus[] commonDeliveryStatusList = {DELIVERING, COMPLETE};

    private CommonDeliveryStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String getValue(){
        return value;
    }

    public static CommonDeliveryStatus[] getCommonDeliveryStatusList() {
        return commonDeliveryStatusList;
    }

    public static CommonDeliveryStatus getEnum(String value) {
        for(CommonDeliveryStatus v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
