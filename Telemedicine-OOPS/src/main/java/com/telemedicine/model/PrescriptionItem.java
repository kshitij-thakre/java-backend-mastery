package com.telemedicine.model;

import com.telemedicine.util.ValidationUtil;

/**
 * Represents a single medicine entry in a prescription.
 * Demonstrates:
 * - Composition component (part-whole relationship with Prescription)
 * - Immutability via final fields
 */
public class PrescriptionItem {

    private final String medicineName;
    private final String dosage;
    private final String frequency;
    private final int durationDays;

    public PrescriptionItem(String medicineName, String dosage, String frequency, int durationDays) {
        ValidationUtil.requireNonBlank(medicineName, "Medicine Name");
        ValidationUtil.requireNonBlank(dosage, "Dosage");
        ValidationUtil.requireNonBlank(frequency, "Frequency");
        ValidationUtil.validatePositive(durationDays, "Duration Days");

        this.medicineName = medicineName.trim();
        this.dosage = dosage.trim();
        this.frequency = frequency.trim();
        this.durationDays = durationDays;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getDurationDays() {
        return durationDays;
    }

    @Override
    public String toString() {
        return String.format("- %s | %s | %s | Duration: %d days",
                medicineName, dosage, frequency, durationDays);
    }
}
