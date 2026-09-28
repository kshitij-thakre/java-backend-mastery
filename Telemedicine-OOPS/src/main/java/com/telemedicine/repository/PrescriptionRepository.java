package com.telemedicine.repository;

import com.telemedicine.model.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends Repository<Prescription, Integer> {

    Optional<Prescription> findByAppointmentId(int appointmentId);

    List<Prescription> findByPatientId(int patientId);
}
