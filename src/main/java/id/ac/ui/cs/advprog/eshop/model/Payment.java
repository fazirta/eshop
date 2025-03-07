package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class Payment {
    String id;
    String method;
    Map<String, String> paymentData;
    String status;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.status = PaymentStatus.WAITING.getValue();

        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid method: " + method);
        }
        this.method = method;

        if ("VOUCHER".equals(method)) {
            validateVoucher(paymentData);
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            validateCashOnDelivery(paymentData);
        }
        this.paymentData = paymentData;
    }

    public Payment(String id, String method, Map<String, String> paymentData, String status) {
        this(id, method, paymentData);
        setStatus(status);
    }

    private void validateVoucher(Map<String, String> paymentData) {
        if (!paymentData.containsKey("voucherCode")) {
            throw new IllegalArgumentException("Voucher code is missing in payment data.");
        }

        String voucherCode = paymentData.get("voucherCode");

        if (voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            setStatus("REJECTED");
            return;
        }

        long digitCount = voucherCode.chars().filter(Character::isDigit).count();
        if (digitCount != 8) {
            setStatus("REJECTED");
        }
    }

    private void validateCashOnDelivery(Map<String, String> paymentData) {
        if (!paymentData.containsKey("address")) {
            throw new IllegalArgumentException("Address is missing in payment data.");
        }
        if (!paymentData.containsKey("deliveryFee")) {
            throw new IllegalArgumentException("Delivery fee is missing in payment data.");
        }
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
