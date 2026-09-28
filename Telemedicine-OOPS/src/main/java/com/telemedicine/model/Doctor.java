package com.telemedicine.model;

import com.telemedicine.util.ValidationUtil;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a Doctor in the telemedicine system.
 * Demonstrates:
 * - Inheritance (IS-A relationship with User)
 * - Method Overriding
 * - State encapsulation with Collections (Set for distinct time slots)
 * - Domain validation
 */
public class Doctor extends User {

    private Specialization specialization;
    private double consultationFee;
    private int experienceYears;
    private final Set<String> availableSlots;

    public Doctor(int id, String name, String email, String phone,
                  Specialization specialization, double consultationFee, int experienceYears) {
        super(id, name, email, phone, UserRole.DOCTOR);
        ValidationUtil.validatePositive(consultationFee, "Consultation Fee");
        ValidationUtil.validateNonNegative(experienceYears, "Experience Years");

        if (specialization == null) {
            throw new IllegalArgumentException("Specialization cannot be null");
        }

        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.experienceYears = experienceYears;
        this.availableSlots = new LinkedHashSet<>();
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        if (specialization == null) {
            throw new IllegalArgumentException("Specialization cannot be null");
        }
        this.specialization = specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        ValidationUtil.validatePositive(consultationFee, "Consultation Fee");
        this.consultationFee = consultationFee;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        ValidationUtil.validateNonNegative(experienceYears, "Experience Years");
        this.experienceYears = experienceYears;
    }

    public Set<String> getAvailableSlots() {
        return Collections.unmodifiableSet(availableSlots);
    }

    public void addAvailableSlot(String slot) {
        ValidationUtil.requireNonBlank(slot, "Slot");
        availableSlots.add(slot.trim());
    }

    public boolean removeAvailableSlot(String slot) {
        if (slot == null) return false;
        return availableSlots.remove(slot.trim());
    }

    public boolean isAvailable(String slot) {
        if (slot == null) return false;
        return availableSlots.contains(slot.trim());
    }

    @Override
    public String getDisplayDetails() {
        return String.format("Dr. %s (ID: %d) | %s | Exp: %d yrs | Fee: $%.2f | Slots Available: %s",
                getName(), getId(), specialization.getDisplayName(), experienceYears,
                consultationFee, availableSlots.isEmpty() ? "[No active slots]" : availableSlots);
    }
}
