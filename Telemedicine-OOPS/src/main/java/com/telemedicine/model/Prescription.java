package com.telemedicine.model;

import com.telemedicine.util.ValidationUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a medical prescription issued by a Doctor to a Patient.
 * Demonstrates:
 * - Composition (HAS-A List of PrescriptionItems, lifecycle tightly coupled)
 * - Association (HAS-A Doctor, HAS-A Patient)
 * - Encapsulation
 */
public class Prescription {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final int prescriptionId;
    private final int appointmentId;
    private final Doctor doctor;
    private final Patient patient;
    private final List<PrescriptionItem> items;
    private String clinicalAdvice;
    private final LocalDateTime issuedAt;

    public Prescription(int prescriptionId, int appointmentId, Doctor doctor, Patient patient, String clinicalAdvice) {
        ValidationUtil.validatePositive(prescriptionId, "Prescription ID");
        ValidationUtil.validatePositive(appointmentId, "Appointment ID");
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }

        this.prescriptionId = prescriptionId;
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patient = patient;
        this.clinicalAdvice = clinicalAdvice != null ? clinicalAdvice.trim() : "";
        this.items = new ArrayList<>();
        this.issuedAt = LocalDateTime.now();
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<PrescriptionItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public String getClinicalAdvice() {
        return clinicalAdvice;
    }

    public void setClinicalAdvice(String clinicalAdvice) {
        this.clinicalAdvice = clinicalAdvice != null ? clinicalAdvice.trim() : "";
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void addItem(PrescriptionItem item) {
        if (item != null) {
            items.add(item);
        }
    }

    public String getFormattedPrescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append(String.format("PRESCRIPTION #%d (Appt #%d)\n", prescriptionId, appointmentId));
        sb.append("Date: ").append(issuedAt.format(FORMATTER)).append("\n");
        sb.append("Doctor: Dr. ").append(doctor.getName()).append(" (").append(doctor.getSpecialization().getDisplayName()).append(")\n");
        sb.append("Patient: ").append(patient.getName()).append(" (Age: ").append(patient.getAge()).append(")\n");
        sb.append("-----------------------------------------\n");
        sb.append("MEDICATIONS:\n");
        if (items.isEmpty()) {
            sb.append("  (No medications listed)\n");
        } else {
            for (PrescriptionItem item : items) {
                sb.append("  ").append(item.toString()).append("\n");
            }
        }
        sb.append("-----------------------------------------\n");
        sb.append("ADVICE / INSTRUCTIONS:\n");
        sb.append("  ").append(clinicalAdvice.isBlank() ? "Standard rest & hydration." : clinicalAdvice).append("\n");
        sb.append("=========================================");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Prescription #%d | Appt #%d | Dr. %s -> %s | Items: %d | Issued: %s",
                prescriptionId, appointmentId, doctor.getName(), patient.getName(), items.size(), issuedAt.format(FORMATTER));
    }
}
