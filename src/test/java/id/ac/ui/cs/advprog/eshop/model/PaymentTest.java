package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void testCreatePaymentVoucherMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "VOUCHER", paymentData);
        assertEquals("VOUCHER", payment.getMethod());
    }

    @Test
    void testCreatePaymentInvalidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "MEOW");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "VOUCHER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka No. 1, Kel. Gambir, Jakarta Pusat, DKI Jakarta, 10110");
        paymentData.put("deliveryFee", "Rp 50.000,-");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "CASH_ON_DELIVERY", paymentData);
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        Map<String, String> paymentData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "VOUCHER", paymentData);
        });
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "MEOW", paymentData);
        });
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "VOUCHER", paymentData);
        assertEquals("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertSame(paymentData, payment.getPaymentData());
        assertEquals("WAITING", payment.getStatus());
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "VOUCHER", paymentData, "SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "VOUCHER", paymentData, "MEOW");
        });
    }

    @Test
    void testSetStatusToRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "VOUCHER", paymentData);
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", "VOUCHER", paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}
