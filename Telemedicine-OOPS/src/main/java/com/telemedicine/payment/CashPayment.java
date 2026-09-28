package com.telemedicine.payment;

import com.telemedicine.model.Payment;
import com.telemedicine.model.PaymentStatus;
import com.telemedicine.util.IdGenerator;
import com.telemedicine.util.ValidationUtil;

import java.util.UUID;

/**
 * Cash / Counter payment implementation.
 * Note: Only implements PaymentGateway (does NOT implement Refundable).
 * Demonstrates Interface Segregation Principle (ISP).
 */
public class CashPayment implements PaymentGateway {

    private final String receiptNumber;

    public CashPayment(String receiptNumber) {
        ValidationUtil.requireNonBlank(receiptNumber, "Cash Receipt Number");
        this.receiptNumber = receiptNumber.trim();
    }

    @Override
    public Payment processPayment(int appointmentId, double amount) {
        ValidationUtil.validatePositive(amount, "Payment amount");
        String txnRef = "CASH-REC-" + receiptNumber.toUpperCase();
        return new Payment(
                IdGenerator.nextPaymentId(),
                appointmentId,
                amount,
                getGatewayName() + " [Receipt: " + receiptNumber + "]",
                PaymentStatus.COMPLETED,
                txnRef
        );
    }

    @Override
    public String getGatewayName() {
        return "Cash / Counter";
    }
}
