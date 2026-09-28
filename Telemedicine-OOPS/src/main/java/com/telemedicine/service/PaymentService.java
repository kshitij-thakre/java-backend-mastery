package com.telemedicine.service;

import com.telemedicine.model.Payment;
import com.telemedicine.payment.PaymentGateway;

import java.util.Optional;

public interface PaymentService {

    Payment processAppointmentPayment(int appointmentId, PaymentGateway gateway);

    Optional<Payment> getPaymentForAppointment(int appointmentId);
}
