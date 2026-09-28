package com.telemedicine.model;

import com.telemedicine.util.ValidationUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a payment transaction record.
 * Demonstrates:
 * - Encapsulation
 * - Immutability of transaction fields
 */
public class Payment {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final int paymentId;
    private final int appointmentId;
    private final double amount;
    private final String paymentMethod;
    private PaymentStatus status;
    private final String transactionRef;
    private final LocalDateTime timestamp;

    public Payment(int paymentId, int appointmentId, double amount, String paymentMethod,
                   PaymentStatus status, String transactionRef) {
        ValidationUtil.validatePositive(paymentId, "Payment ID");
        ValidationUtil.validatePositive(appointmentId, "Appointment ID");
        ValidationUtil.validatePositive(amount, "Amount");
        ValidationUtil.requireNonBlank(paymentMethod, "Payment Method");
        ValidationUtil.requireNonBlank(transactionRef, "Transaction Ref");

        this.paymentId = paymentId;
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod.trim();
        this.status = status != null ? status : PaymentStatus.PENDING;
        this.transactionRef = transactionRef.trim();
        this.timestamp = LocalDateTime.now();
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Payment #%d | Appt #%d | Amount: $%.2f | Method: %s | Status: %s | Ref: %s | Time: %s",
                paymentId, appointmentId, amount, paymentMethod, status, transactionRef, timestamp.format(FORMATTER));
    }
}
