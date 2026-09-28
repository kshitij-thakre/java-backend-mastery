package com.telemedicine.payment;

import com.telemedicine.exception.PaymentFailedException;
import com.telemedicine.model.Payment;
import com.telemedicine.model.PaymentStatus;
import com.telemedicine.util.IdGenerator;
import com.telemedicine.util.ValidationUtil;

import java.util.UUID;

/**
 * Credit/Debit Card payment implementation.
 * Demonstrates multiple interfaces, data masking for security, and validation.
 */
public class CardPayment implements PaymentGateway, Refundable {

    private final String cardNumber;
    private final String cardHolderName;

    public CardPayment(String cardNumber, String cardHolderName, String cvv) {
        ValidationUtil.requireNonBlank(cardNumber, "Card number");
        ValidationUtil.requireNonBlank(cardHolderName, "Cardholder name");
        ValidationUtil.requireNonBlank(cvv, "CVV");

        String sanitizedCard = cardNumber.replaceAll("\\s+", "");
        if (sanitizedCard.length() < 12 || sanitizedCard.length() > 19) {
            throw new PaymentFailedException("Invalid card number length.");
        }
        if (cvv.trim().length() < 3 || cvv.trim().length() > 4) {
            throw new PaymentFailedException("Invalid CVV length.");
        }

        this.cardNumber = sanitizedCard;
        this.cardHolderName = cardHolderName.trim();
    }

    private String getMaskedCardNumber() {
        if (cardNumber.length() <= 4) return "****";
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

    @Override
    public Payment processPayment(int appointmentId, double amount) {
        ValidationUtil.validatePositive(amount, "Payment amount");
        String txnRef = "CARD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new Payment(
                IdGenerator.nextPaymentId(),
                appointmentId,
                amount,
                getGatewayName() + " (" + getMaskedCardNumber() + ")",
                PaymentStatus.COMPLETED,
                txnRef
        );
    }

    @Override
    public boolean processRefund(String transactionRef, double amount) {
        System.out.printf("[Card Refund] Refund of $%.2f processed to card ending in %s for Txn: %s%n",
                amount, cardNumber.substring(cardNumber.length() - 4), transactionRef);
        return true;
    }

    @Override
    public String getGatewayName() {
        return "Credit/Debit Card";
    }
}
