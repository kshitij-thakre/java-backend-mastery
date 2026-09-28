package com.telemedicine.model;

import com.telemedicine.exception.InvalidAppointmentStateException;
import com.telemedicine.util.ValidationUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an active or completed consultation session.
 * Demonstrates:
 * - Association with Appointment
 * - Composition of clinical notes (List<String>)
 * - Invariant state transitions
 */
public class Consultation {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final int consultationId;
    private final Appointment appointment;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private ConsultationStatus status;
    private final List<String> notes;

    public Consultation(int consultationId, Appointment appointment) {
        ValidationUtil.validatePositive(consultationId, "Consultation ID");
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null for consultation");
        }

        this.consultationId = consultationId;
        this.appointment = appointment;
        this.startTime = LocalDateTime.now();
        this.status = ConsultationStatus.IN_PROGRESS;
        this.notes = new ArrayList<>();

        // Update linked appointment state
        appointment.startConsultation();
    }

    public int getConsultationId() {
        return consultationId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ConsultationStatus getStatus() {
        return status;
    }

    public List<String> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    public void addNote(String note) {
        if (status == ConsultationStatus.COMPLETED) {
            throw new InvalidAppointmentStateException("Cannot add notes to a completed consultation.");
        }
        ValidationUtil.requireNonBlank(note, "Consultation note");
        this.notes.add(note.trim());
    }

    public void completeConsultation() {
        if (this.status == ConsultationStatus.COMPLETED) {
            throw new InvalidAppointmentStateException("Consultation is already completed.");
        }
        this.status = ConsultationStatus.COMPLETED;
        this.endTime = LocalDateTime.now();
        this.appointment.completeConsultation();
    }

    @Override
    public String toString() {
        return String.format(
                "Consultation #%d | Appt #%d | Doctor: Dr. %s | Patient: %s | Status: %s | Started: %s | Notes Count: %d",
                consultationId, appointment.getAppointmentId(),
                appointment.getDoctor().getName(), appointment.getPatient().getName(),
                status, startTime.format(FORMATTER), notes.size());
    }
}
