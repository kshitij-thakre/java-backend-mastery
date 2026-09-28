package com.telemedicine.repository;

import com.telemedicine.model.Consultation;

import java.util.Optional;

public interface ConsultationRepository extends Repository<Consultation, Integer> {

    Optional<Consultation> findByAppointmentId(int appointmentId);
}
