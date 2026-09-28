package com.telemedicine.repository;

import com.telemedicine.model.Payment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<Integer, Payment> paymentStorage = new LinkedHashMap<>();

    @Override
    public Payment save(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        paymentStorage.put(payment.getPaymentId(), payment);
        return payment;
    }

    @Override
    public Optional<Payment> findById(Integer id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(paymentStorage.get(id));
    }

    @Override
    public List<Payment> findAll() {
        return new ArrayList<>(paymentStorage.values());
    }

    @Override
    public boolean existsById(Integer id) {
        return id != null && paymentStorage.containsKey(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        return paymentStorage.remove(id) != null;
    }

    @Override
    public Optional<Payment> findByAppointmentId(int appointmentId) {
        return paymentStorage.values().stream()
                .filter(p -> p.getAppointmentId() == appointmentId)
                .findFirst();
    }
}
