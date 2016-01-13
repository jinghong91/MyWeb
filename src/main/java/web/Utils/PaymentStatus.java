package web.Utils;


public enum PaymentStatus {
    NOT_PAY("notPay"), PART_PAID("partPaid"), PAID("paid");

    private final String value;
    private static final String[] paymentStatusList = {NOT_PAY.toString(), PART_PAID.toString(), PAID.toString()};

    private PaymentStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public static String[] getPaymentStatusList() {
        return paymentStatusList;
    }
}

