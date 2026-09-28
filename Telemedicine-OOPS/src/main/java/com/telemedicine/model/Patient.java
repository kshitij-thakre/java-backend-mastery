package com.telemedicine.model;

import com.telemedicine.util.ValidationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Patient in the telemedicine system.
 * Demonstrates:
 * - Inheritance (IS-A relationship with User via 'extends')
 * - Super constructor invocation ('super')
 * - Constructor Chaining ('this')
 * - Method Overriding ('@Override getDisplayDetails()')
 * - Encapsulation of specialized fields (age, medical history)
 */
public class Patient extends User {

    private int age;
    private String bloodGroup;
    private final List<String> medicalHistory;

    // Full Parameterized Constructor
    public Patient(int id, String name, String email, String phone, int age, String bloodGroup) {
        super(id, name, email, phone, UserRole.PATIENT);
        ValidationUtil.validatePositive(age, "Age");
        this.age = age;
        this.bloodGroup = (bloodGroup != null && !bloodGroup.isBlank()) ? bloodGroup.trim().toUpperCase() : "Unknown";
        this.medicalHistory = new ArrayList<>();
    }

    // Overloaded Constructor for quick registration (Constructor Chaining)
    public Patient(int id, String name, String email, String phone, int age) {
        this(id, name, email, phone, age, "Unknown");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ValidationUtil.validatePositive(age, "Age");
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = (bloodGroup != null && !bloodGroup.isBlank()) ? bloodGroup.trim().toUpperCase() : "Unknown";
    }

    public List<String> getMedicalHistory() {
        // Defensive copying / unmodifiable view to protect encapsulation
        return Collections.unmodifiableList(medicalHistory);
    }

    public void addMedicalRecord(String record) {
        if (record != null && !record.isBlank()) {
            medicalHistory.add(record.trim());
        }
    }

    @Override
    public String getDisplayDetails() {
        return String.format("Patient #%d: %s (Age: %d, Blood Group: %s) | Contact: %s | Email: %s | History Records: %d",
                getId(), getName(), age, bloodGroup, getPhone(), getEmail(), medicalHistory.size());
    }
}
