package com.telemedicine.model;

public enum Specialization {
    GENERAL_PHYSICIAN("General Physician"),
    CARDIOLOGY("Cardiology"),
    DERMATOLOGY("Dermatology"),
    PEDIATRICS("Pediatrics"),
    NEUROLOGY("Neurology"),
    ORTHOPEDICS("Orthopedics"),
    PSYCHIATRY("Psychiatry");

    private final String displayName;

    Specialization(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
