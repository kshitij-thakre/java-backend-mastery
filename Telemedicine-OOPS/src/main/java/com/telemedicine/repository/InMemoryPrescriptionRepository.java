package com.telemedicine.repository;

import com.telemedicine.model.Prescription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryPrescriptionRepository implements PrescriptionRepository {

    private final Map<Integer, Prescription> prescriptionStorage = new LinkedHashMap<>();

    @Override
    public Prescription save(Prescription prescription) {
        if (prescription == null) {
            throw new IllegalArgumentException("Prescription cannot be null");
        }
        prescriptionStorage.put(prescription.getPrescriptionId(), prescription);
        return prescription;
    }

    @Override
    public Optional<Prescription> findById(Integer id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(prescriptionStorage.get(id));
    }

    @Override
    public List<Prescription> findAll() {
        return new ArrayList<>(prescriptionStorage.values());
    }

    @Override
    public boolean existsById(Integer id) {
        return id != null && prescriptionStorage.containsKey(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        return prescriptionStorage.remove(id) != null;
    }

    @Override
    public Optional<Prescription> findByAppointmentId(int appointmentId) {
        return prescriptionStorage.values().stream()
                .filter(p -> p.getAppointmentId() == appointmentId)
                .findFirst();
    }

    @Override
    public List<Prescription> findByPatientId(int patientId) {
        List<Prescription> list = new ArrayList<>();
        for (Prescription p : prescriptionStorage.values()) {
            if (p.getPatient().getId() == patientId) {
                list.add(p);
            }
        }
        return Collections.unmodifiableList(list);
    }
}
