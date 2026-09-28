package com.telemedicine.payment;

import com.telemedicine.exception.PaymentFailedException;
import com.telemedicine.model.Payment;
import com.telemedicine.model.PaymentStatus;
import com.telemedicine.util.IdGenerator;
import com.telemedicine.util.ValidationUtil;

import java.util.UUID;

/**
 * UPI Payment implementation.
 * Demonstrates implementing multiple interfaces (PaymentGateway, Refundable).
 */
public class UpiPayment implements PaymentGateway, Refundable {

    private final String upiId;

    public UpiPayment(String upiId) {
        ValidationUtil.requireNonBlank(upiId, "UPI ID");
        if (!upiId.contains("@")) {
            throw new PaymentFailedException("Invalid UPI ID format. Expected format: username@bank");
        }
        this.upiId = upiId.trim();
    }

    public String getUpiId() {
        return upiId;
    }

    @Override
    public Payment processPayment(int appointmentId, double amount) {
        ValidationUtil.validatePositive(amount, "Payment amount");
        String txnRef = "UPI-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new Payment(
                IdGenerator.nextPaymentId(),
                appointmentId,
                amount,
                getGatewayName() + " (" + upiId + ")",
                PaymentStatus.COMPLETED,
                txnRef
        );
    }

    @Override
    public boolean processRefund(String transactionRef, double amount) {
        System.out.printf("[UPI Refund] Initiated refund of $%.2f to %s for Txn: %s%n",
                amount, upiId, transactionRef);
        return true;
    }

    @Override
    public String getGatewayName() {
        return "UPI";
    }
}
