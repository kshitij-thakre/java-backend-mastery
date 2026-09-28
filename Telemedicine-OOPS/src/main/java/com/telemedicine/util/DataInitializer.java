package com.telemedicine.util;

import com.telemedicine.model.Appointment;
import com.telemedicine.model.Doctor;
import com.telemedicine.model.Patient;
import com.telemedicine.model.Specialization;
import com.telemedicine.service.AppointmentService;
import com.telemedicine.service.DoctorService;
import com.telemedicine.service.UserService;

/**
 * Initializes foundational seed data so the application is immediately interactive.
 */
public final class DataInitializer {

    private DataInitializer() {
    }

    public static void initializeSampleData(UserService userService,
                                            DoctorService doctorService,
                                            AppointmentService appointmentService) {

        // Register Doctors
        Doctor doc1 = userService.registerDoctor(
                "Sarah Connor",
                "sarah.connor@telemed.com",
                "+1-555-0101",
                Specialization.CARDIOLOGY,
                75.0,
                12
        );
        doctorService.addAvailableSlot(doc1.getId(), "2026-10-01 09:00");
        doctorService.addAvailableSlot(doc1.getId(), "2026-10-01 10:30");
        doctorService.addAvailableSlot(doc1.getId(), "2026-10-01 14:00");

        Doctor doc2 = userService.registerDoctor(
                "Rajesh Sharma",
                "rajesh.sharma@telemed.com",
                "+91-9876543210",
                Specialization.GENERAL_PHYSICIAN,
                40.0,
                8
        );
        doctorService.addAvailableSlot(doc2.getId(), "2026-10-01 11:00");
        doctorService.addAvailableSlot(doc2.getId(), "2026-10-01 15:00");
        doctorService.addAvailableSlot(doc2.getId(), "2026-10-02 10:00");

        Doctor doc3 = userService.registerDoctor(
                "Emily Watson",
                "emily.watson@telemed.com",
                "+44-7700900077",
                Specialization.DERMATOLOGY,
                60.0,
                10
        );
        doctorService.addAvailableSlot(doc3.getId(), "2026-10-02 11:30");
        doctorService.addAvailableSlot(doc3.getId(), "2026-10-02 16:00");

        // Register Patients
        Patient p1 = userService.registerPatient(
                "Alice Smith",
                "alice.smith@example.com",
                "+1-555-0202",
                29,
                "O+"
        );
        p1.addMedicalRecord("Seasonal Allergies");

        Patient p2 = userService.registerPatient(
                "Bob Johnson",
                "bob.j@example.com",
                "+1-555-0303",
                45,
                "A+"
        );
        p2.addMedicalRecord("Hypertension");

        Patient p3 = userService.registerPatient(
                "Charlie Brown",
                "charlie.b@example.com",
                "+1-555-0404",
                34,
                "B+"
        );

        // Pre-book a sample appointment
        Appointment sampleAppt = appointmentService.bookAppointment(
                p1.getId(),
                doc1.getId(),
                "2026-10-01 09:00"
        );
    }
}
