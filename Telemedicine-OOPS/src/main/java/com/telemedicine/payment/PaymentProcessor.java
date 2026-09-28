package com.telemedicine.payment;

import com.telemedicine.model.Payment;

/**
 * Strategy executor for processing payments.
 * Demonstrates:
 * - Dependency Inversion Principle (DIP): Depends on PaymentGateway interface, not concrete implementations.
 * - Runtime Polymorphism: Dynamically delegates payment execution to the injected gateway.
 * - Loose Coupling
 */
public class PaymentProcessor {

    private PaymentGateway paymentGateway;

    public PaymentProcessor(PaymentGateway paymentGateway) {
        if (paymentGateway == null) {
            throw new IllegalArgumentException("PaymentGateway cannot be null");
        }
        this.paymentGateway = paymentGateway;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        if (paymentGateway == null) {
            throw new IllegalArgumentException("PaymentGateway cannot be null");
        }
        this.paymentGateway = paymentGateway;
    }

    public Payment executePayment(int appointmentId, double amount) {
        System.out.printf("[PaymentGateway: %s] Processing charge of $%.2f for Appt #%d...%n",
                paymentGateway.getGatewayName(), amount, appointmentId);
        Payment payment = paymentGateway.processPayment(appointmentId, amount);
        System.out.printf("[Payment Successful] Txn Ref: %s | Status: %s%n",
                payment.getTransactionRef(), payment.getStatus());
        return payment;
    }
}
