package com.telemedicine.payment;

import com.telemedicine.model.Payment;

/**
 * Interface defining the contract for payment gateways.
 * Demonstrates:
 * - Interface-based Polymorphism
 * - Open/Closed Principle (New payment methods can be added without modifying existing code)
 * - Dependency Inversion Principle
 */
public interface PaymentGateway {

    Payment processPayment(int appointmentId, double amount);

    String getGatewayName();
}
