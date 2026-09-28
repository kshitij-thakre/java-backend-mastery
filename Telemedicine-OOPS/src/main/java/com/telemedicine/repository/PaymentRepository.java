package com.telemedicine.repository;

import com.telemedicine.model.Payment;

import java.util.Optional;

public interface PaymentRepository extends Repository<Payment, Integer> {

    Optional<Payment> findByAppointmentId(int appointmentId);
}
