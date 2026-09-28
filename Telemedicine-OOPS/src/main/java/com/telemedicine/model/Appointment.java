package com.telemedicine.model;

import com.telemedicine.exception.InvalidAppointmentStateException;
import com.telemedicine.util.ValidationUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an appointment booked by a Patient with a Doctor.
 * Demonstrates:
 * - Association / HAS-A Relationship (Appointment HAS-A Patient and HAS-A Doctor)
 * - State Management & Invariant Protection
 * - Composition of Status Enums
 */
public class Appointment {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final int appointmentId;
    private final Patient patient;
    private final Doctor doctor;
    private final String timeSlot;
    private final LocalDateTime bookedAt;
    private AppointmentStatus status;
    private PaymentStatus paymentStatus;
    private final double consultationFee;

    public Appointment(int appointmentId, Patient patient, Doctor doctor, String timeSlot) {
        ValidationUtil.validatePositive(appointmentId, "Appointment ID");
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null for an appointment");
        }
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null for an appointment");
        }
        ValidationUtil.requireNonBlank(timeSlot, "Time Slot");

        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.timeSlot = timeSlot.trim();
        this.bookedAt = LocalDateTime.now();
        this.status = AppointmentStatus.BOOKED;
        this.paymentStatus = PaymentStatus.PENDING;
        this.consultationFee = doctor.getConsultationFee();
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void markPaid() {
        if (this.paymentStatus == PaymentStatus.COMPLETED) {
            throw new InvalidAppointmentStateException("Appointment is already paid.");
        }
        this.paymentStatus = PaymentStatus.COMPLETED;
        if (this.status == AppointmentStatus.BOOKED) {
            this.status = AppointmentStatus.CONFIRMED;
        }
    }

    public void startConsultation() {
        if (this.status == AppointmentStatus.CANCELLED) {
            throw new InvalidAppointmentStateException("Cannot start consultation for a cancelled appointment.");
        }
        if (this.status == AppointmentStatus.COMPLETED) {
            throw new InvalidAppointmentStateException("Consultation is already completed.");
        }
        this.status = AppointmentStatus.IN_CONSULTATION;
    }

    public void completeConsultation() {
        if (this.status != AppointmentStatus.IN_CONSULTATION && this.status != AppointmentStatus.CONFIRMED && this.status != AppointmentStatus.BOOKED) {
            throw new InvalidAppointmentStateException("Cannot complete appointment in status: " + this.status);
        }
        this.status = AppointmentStatus.COMPLETED;
    }

    public void cancel() {
        if (this.status == AppointmentStatus.COMPLETED) {
            throw new InvalidAppointmentStateException("Cannot cancel an already completed appointment.");
        }
        if (this.status == AppointmentStatus.CANCELLED) {
            throw new InvalidAppointmentStateException("Appointment is already cancelled.");
        }
        this.status = AppointmentStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return String.format(
                "Appointment #%d | Slot: %s | Doctor: Dr. %s (%s) | Patient: %s (ID: %d) | Status: %s | Payment: %s ($%.2f) | Booked: %s",
                appointmentId, timeSlot, doctor.getName(), doctor.getSpecialization().getDisplayName(),
                patient.getName(), patient.getId(), status, paymentStatus, consultationFee,
                bookedAt.format(FORMATTER));
    }
}
