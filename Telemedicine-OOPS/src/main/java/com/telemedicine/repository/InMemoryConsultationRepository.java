package com.telemedicine.repository;

import com.telemedicine.model.Consultation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryConsultationRepository implements ConsultationRepository {

    private final Map<Integer, Consultation> consultationStorage = new LinkedHashMap<>();

    @Override
    public Consultation save(Consultation consultation) {
        if (consultation == null) {
            throw new IllegalArgumentException("Consultation cannot be null");
        }
        consultationStorage.put(consultation.getConsultationId(), consultation);
        return consultation;
    }

    @Override
    public Optional<Consultation> findById(Integer id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(consultationStorage.get(id));
    }

    @Override
    public List<Consultation> findAll() {
        return new ArrayList<>(consultationStorage.values());
    }

    @Override
    public boolean existsById(Integer id) {
        return id != null && consultationStorage.containsKey(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        return consultationStorage.remove(id) != null;
    }

    @Override
    public Optional<Consultation> findByAppointmentId(int appointmentId) {
        return consultationStorage.values().stream()
                .filter(c -> c.getAppointment().getAppointmentId() == appointmentId)
                .findFirst();
    }
}
