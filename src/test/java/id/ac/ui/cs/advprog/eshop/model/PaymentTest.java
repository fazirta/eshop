package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void testCreatePaymentVoucherMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
    }

    @Test
    void testCreatePaymentInvalidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "MEOW");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka No. 1, Kel. Gambir, Jakarta Pusat, DKI Jakarta, 10110");
        paymentData.put("deliveryFee", "Rp 50.000,-");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.CASH_ON_DELIVERY.getValue(), paymentData);
        assertEquals(PaymentMethod.CASH_ON_DELIVERY.getValue(), payment.getMethod());
    }

    @Test
    void testCreatePaymentEmptyPaymentData() {
        Map<String, String> paymentData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.VOUCHER.getValue(), paymentData);
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
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.VOUCHER.getValue(), paymentData);
        assertEquals("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", payment.getId());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());
        assertSame(paymentData, payment.getPaymentData());
        assertEquals(PaymentStatus.WAITING.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.VOUCHER.getValue(), paymentData, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.VOUCHER.getValue(), paymentData, "MEOW");
        });
    }

    @Test
    void testSetStatusToRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.VOUCHER.getValue(), paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("b0df50fd-5e7d-4a4c-8ee9-6c9576ec2f5d", PaymentMethod.VOUCHER.getValue(), paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}
