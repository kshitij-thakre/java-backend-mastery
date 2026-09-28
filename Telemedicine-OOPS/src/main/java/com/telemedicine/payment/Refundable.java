package com.telemedicine.payment;

/**
 * Interface Segregation Principle: Only digital gateways that support refunds implement this.
 * Demonstrates:
 * - Interface Segregation Principle (ISP)
 * - Multiple Interfaces (classes can implement both PaymentGateway and Refundable)
 */
public interface Refundable {

    boolean processRefund(String transactionRef, double amount);
}
