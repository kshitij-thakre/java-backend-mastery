package com.telemedicine.service;

import com.telemedicine.model.Consultation;

import java.util.Optional;

public interface ConsultationService {

    Consultation startConsultation(int appointmentId);

    Consultation addConsultationNote(int consultationId, String note);

    Consultation completeConsultation(int consultationId);

    Consultation getConsultationById(int consultationId);

    Optional<Consultation> getConsultationByAppointmentId(int appointmentId);
}
