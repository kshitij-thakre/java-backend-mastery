package com.telemedicine.service;

import com.telemedicine.model.Appointment;
import com.telemedicine.model.Payment;
import com.telemedicine.notification.NotificationDispatcher;
import com.telemedicine.payment.PaymentGateway;
import com.telemedicine.payment.PaymentProcessor;
import com.telemedicine.repository.PaymentRepository;

import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentService appointmentService;
    private final NotificationDispatcher notificationDispatcher;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              AppointmentService appointmentService,
                              NotificationDispatcher notificationDispatcher) {
        if (paymentRepository == null || appointmentService == null || notificationDispatcher == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.paymentRepository = paymentRepository;
        this.appointmentService = appointmentService;
        this.notificationDispatcher = notificationDispatcher;
    }

    @Override
    public Payment processAppointmentPayment(int appointmentId, PaymentGateway gateway) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        double fee = appointment.getConsultationFee();

        PaymentProcessor processor = new PaymentProcessor(gateway);
        Payment payment = processor.executePayment(appointmentId, fee);

        // Update appointment status to paid / confirmed
        appointment.markPaid();
        paymentRepository.save(payment);

        // Notification
        String receipt = String.format("Payment received: $%.2f for Appt #%d via %s (Txn: %s).",
                fee, appointmentId, gateway.getGatewayName(), payment.getTransactionRef());
        notificationDispatcher.broadcast(appointment.getPatient(), receipt);

        return payment;
    }

    @Override
    public Optional<Payment> getPaymentForAppointment(int appointmentId) {
        return paymentRepository.findByAppointmentId(appointmentId);
    }
}
