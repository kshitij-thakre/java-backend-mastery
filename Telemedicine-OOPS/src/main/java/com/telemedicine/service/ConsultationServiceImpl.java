package com.telemedicine.service;

import com.telemedicine.exception.TelemedicineException;
import com.telemedicine.model.Appointment;
import com.telemedicine.model.Consultation;
import com.telemedicine.notification.NotificationDispatcher;
import com.telemedicine.repository.ConsultationRepository;
import com.telemedicine.util.IdGenerator;

import java.util.Optional;

public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentService appointmentService;
    private final NotificationDispatcher notificationDispatcher;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository,
                                   AppointmentService appointmentService,
                                   NotificationDispatcher notificationDispatcher) {
        if (consultationRepository == null || appointmentService == null || notificationDispatcher == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.consultationRepository = consultationRepository;
        this.appointmentService = appointmentService;
        this.notificationDispatcher = notificationDispatcher;
    }

    @Override
    public Consultation startConsultation(int appointmentId) {
        Optional<Consultation> existing = consultationRepository.findByAppointmentId(appointmentId);
        if (existing.isPresent()) {
            return existing.get();
        }

        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        int consultationId = IdGenerator.nextConsultationId();
        Consultation consultation = new Consultation(consultationId, appointment);

        consultationRepository.save(consultation);

        String startMsg = String.format("Consultation #%d started for Appt #%d with Dr. %s.",
                consultationId, appointmentId, appointment.getDoctor().getName());
        notificationDispatcher.broadcast(appointment.getPatient(), startMsg);

        return consultation;
    }

    @Override
    public Consultation addConsultationNote(int consultationId, String note) {
        Consultation consultation = getConsultationById(consultationId);
        consultation.addNote(note);
        consultationRepository.save(consultation);
        return consultation;
    }

    @Override
    public Consultation completeConsultation(int consultationId) {
        Consultation consultation = getConsultationById(consultationId);
        consultation.completeConsultation();
        consultationRepository.save(consultation);

        Appointment appt = consultation.getAppointment();
        String doneMsg = String.format("Consultation #%d for Appt #%d has been completed.",
                consultationId, appt.getAppointmentId());
        notificationDispatcher.broadcast(appt.getPatient(), doneMsg);
        notificationDispatcher.broadcast(appt.getDoctor(), doneMsg);

        return consultation;
    }

    @Override
    public Consultation getConsultationById(int consultationId) {
        return consultationRepository.findById(consultationId)
                .orElseThrow(() -> new TelemedicineException("Consultation not found with ID: " + consultationId));
    }

    @Override
    public Optional<Consultation> getConsultationByAppointmentId(int appointmentId) {
        return consultationRepository.findByAppointmentId(appointmentId);
    }
}
