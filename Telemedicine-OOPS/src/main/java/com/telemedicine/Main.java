package com.telemedicine;

import com.telemedicine.exception.TelemedicineException;
import com.telemedicine.model.Appointment;
import com.telemedicine.model.Consultation;
import com.telemedicine.model.Doctor;
import com.telemedicine.model.Patient;
import com.telemedicine.model.Payment;
import com.telemedicine.model.Prescription;
import com.telemedicine.model.PrescriptionItem;
import com.telemedicine.model.Specialization;
import com.telemedicine.model.User;
import com.telemedicine.notification.NotificationDispatcher;
import com.telemedicine.payment.CardPayment;
import com.telemedicine.payment.CashPayment;
import com.telemedicine.payment.PaymentGateway;
import com.telemedicine.payment.UpiPayment;
import com.telemedicine.repository.AppointmentRepository;
import com.telemedicine.repository.ConsultationRepository;
import com.telemedicine.repository.InMemoryAppointmentRepository;
import com.telemedicine.repository.InMemoryConsultationRepository;
import com.telemedicine.repository.InMemoryPaymentRepository;
import com.telemedicine.repository.InMemoryPrescriptionRepository;
import com.telemedicine.repository.InMemoryUserRepository;
import com.telemedicine.repository.PaymentRepository;
import com.telemedicine.repository.PrescriptionRepository;
import com.telemedicine.repository.UserRepository;
import com.telemedicine.service.AppointmentService;
import com.telemedicine.service.AppointmentServiceImpl;
import com.telemedicine.service.ConsultationService;
import com.telemedicine.service.ConsultationServiceImpl;
import com.telemedicine.service.DoctorService;
import com.telemedicine.service.DoctorServiceImpl;
import com.telemedicine.service.PaymentService;
import com.telemedicine.service.PaymentServiceImpl;
import com.telemedicine.service.PrescriptionService;
import com.telemedicine.service.PrescriptionServiceImpl;
import com.telemedicine.service.UserService;
import com.telemedicine.service.UserServiceImpl;
import com.telemedicine.util.DataInitializer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

/**
 * Main application entry point for the Telemedicine Console System.
 * Demonstrates:
 * - Loose Coupling & Dependency Injection
 * - Separation of Concerns
 * - Clean terminal interaction
 */
public class Main {

    private final UserService userService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PaymentService paymentService;
    private final ConsultationService consultationService;
    private final PrescriptionService prescriptionService;
    private final Scanner scanner;

    public Main() {
        // Wire up repositories (In-Memory collections)
        UserRepository userRepository = new InMemoryUserRepository();
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();
        ConsultationRepository consultationRepository = new InMemoryConsultationRepository();
        PrescriptionRepository prescriptionRepository = new InMemoryPrescriptionRepository();
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();

        // Notification dispatcher (Composition/Aggregation)
        NotificationDispatcher notificationDispatcher = new NotificationDispatcher();

        // Wire up services (Dependency Inversion)
        this.userService = new UserServiceImpl(userRepository);
        this.doctorService = new DoctorServiceImpl(userRepository, userService);
        this.appointmentService = new AppointmentServiceImpl(appointmentRepository, userService, notificationDispatcher);
        this.paymentService = new PaymentServiceImpl(paymentRepository, appointmentService, notificationDispatcher);
        this.consultationService = new ConsultationServiceImpl(consultationRepository, appointmentService, notificationDispatcher);
        this.prescriptionService = new PrescriptionServiceImpl(prescriptionRepository, appointmentService, notificationDispatcher);

        this.scanner = new Scanner(System.in);

        // Seed initial data for quick testing
        DataInitializer.initializeSampleData(userService, doctorService, appointmentService);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    public void run() {
        System.out.println("==================================================");
        System.out.println(" Welcome to Telemedicine OOP System (Java 17)");
        System.out.println(" Sample Doctors, Patients & Slots pre-loaded!");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            System.out.println();
            try {
                switch (choice) {
                    case 1 -> handleRegisterPatient();
                    case 2 -> handleRegisterDoctor();
                    case 3 -> handleViewPatients();
                    case 4 -> handleViewDoctors();
                    case 5 -> handleViewDoctorAvailability();
                    case 6 -> handleBookAppointment();
                    case 7 -> handleCancelAppointment();
                    case 8 -> handleViewAppointments();
                    case 9 -> handleMakePayment();
                    case 10 -> handleStartConsultation();
                    case 11 -> handleAddConsultationNotes();
                    case 12 -> handleCreatePrescription();
                    case 13 -> handleViewPrescription();
                    case 14 -> handleViewAppointmentHistory();
                    case 0 -> {
                        System.out.println("Exiting Telemedicine System. Thank you!");
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please choose a valid option (0-14).");
                }
            } catch (TelemedicineException ex) {
                System.out.println(" [ERROR] " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println(" [UNEXPECTED ERROR] " + ex.getMessage());
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("===== TELEMEDICINE SYSTEM =====");
        System.out.println("1. Register Patient");
        System.out.println("2. Register Doctor");
        System.out.println("3. View Patients");
        System.out.println("4. View Doctors");
        System.out.println("5. View Doctor Availability");
        System.out.println("6. Book Appointment");
        System.out.println("7. Cancel Appointment");
        System.out.println("8. View Appointments");
        System.out.println("9. Make Payment");
        System.out.println("10. Start Consultation");
        System.out.println("11. Add Consultation Notes");
        System.out.println("12. Create Prescription");
        System.out.println("13. View Prescription");
        System.out.println("14. View Appointment History");
        System.out.println("0. Exit");
        System.out.println("===============================");
    }

    private void handleRegisterPatient() {
        System.out.println("--- Register Patient ---");
        String name = readString("Enter patient name: ");
        String email = readString("Enter email: ");
        String phone = readString("Enter phone: ");
        int age = readInt("Enter age: ");
        String bloodGroup = readString("Enter blood group (e.g. O+, A+, B+): ");

        Patient patient = userService.registerPatient(name, email, phone, age, bloodGroup);
        System.out.println(" Patient registered successfully with ID: " + patient.getId());
    }

    private void handleRegisterDoctor() {
        System.out.println("--- Register Doctor ---");
        String name = readString("Enter doctor name: ");
        String email = readString("Enter email: ");
        String phone = readString("Enter phone: ");

        System.out.println("Available Specializations:");
        Specialization[] specializations = Specialization.values();
        for (int i = 0; i < specializations.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, specializations[i].getDisplayName());
        }
        int specChoice = readInt("Select Specialization (1-" + specializations.length + "): ");
        if (specChoice < 1 || specChoice > specializations.length) {
            System.out.println("Invalid specialization selection.");
            return;
        }
        Specialization selectedSpec = specializations[specChoice - 1];

        double fee = readDouble("Enter consultation fee ($): ");
        int exp = readInt("Enter years of experience: ");

        Doctor doctor = userService.registerDoctor(name, email, phone, selectedSpec, fee, exp);
        System.out.println(" Doctor registered successfully with ID: " + doctor.getId());

        String addSlot = readString("Would you like to add an initial availability slot? (y/n): ");
        if (addSlot.equalsIgnoreCase("y")) {
            String slot = readString("Enter slot (e.g. 2026-10-01 10:00): ");
            doctorService.addAvailableSlot(doctor.getId(), slot);
            System.out.println(" Slot added successfully.");
        }
    }

    private void handleViewPatients() {
        System.out.println("--- Registered Patients ---");
        List<Patient> patients = userService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        // Demonstrates Upcasting and Runtime Polymorphism
        for (User user : patients) {
            System.out.println(user.getDisplayDetails());
        }
    }

    private void handleViewDoctors() {
        System.out.println("--- Registered Doctors ---");
        List<Doctor> doctors = userService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        // Demonstrates Upcasting and Runtime Polymorphism
        for (User user : doctors) {
            System.out.println(user.getDisplayDetails());
        }
    }

    private void handleViewDoctorAvailability() {
        System.out.println("--- Doctor Availability ---");
        int doctorId = readInt("Enter Doctor ID: ");
        Doctor doctor = userService.getDoctorById(doctorId);
        Set<String> slots = doctorService.getAvailableSlots(doctorId);

        System.out.printf("Availability for Dr. %s (%s):%n",
                doctor.getName(), doctor.getSpecialization().getDisplayName());

        if (slots.isEmpty()) {
            System.out.println("  (No available slots currently)");
        } else {
            for (String slot : slots) {
                System.out.println("  - " + slot);
            }
        }

        String addMore = readString("Add a new slot for this doctor? (y/n): ");
        if (addMore.equalsIgnoreCase("y")) {
            String newSlot = readString("Enter new slot (e.g. 2026-10-02 14:00): ");
            doctorService.addAvailableSlot(doctorId, newSlot);
            System.out.println(" New slot added: " + newSlot);
        }
    }

    private void handleBookAppointment() {
        System.out.println("--- Book Appointment ---");
        int patientId = readInt("Enter Patient ID: ");
        int doctorId = readInt("Enter Doctor ID: ");

        Doctor doctor = userService.getDoctorById(doctorId);
        Set<String> slots = doctor.getAvailableSlots();
        if (slots.isEmpty()) {
            System.out.println("Dr. " + doctor.getName() + " has no available slots.");
            return;
        }

        System.out.println("Available slots for Dr. " + doctor.getName() + ":");
        for (String s : slots) {
            System.out.println("  - " + s);
        }

        String chosenSlot = readString("Enter chosen slot: ");
        Appointment appt = appointmentService.bookAppointment(patientId, doctorId, chosenSlot);
        System.out.println(" Appointment booked successfully!");
        System.out.println("Details: " + appt);
    }

    private void handleCancelAppointment() {
        System.out.println("--- Cancel Appointment ---");
        int apptId = readInt("Enter Appointment ID to cancel: ");
        Appointment appt = appointmentService.cancelAppointment(apptId);
        System.out.println(" Appointment #" + appt.getAppointmentId() + " has been cancelled.");
    }

    private void handleViewAppointments() {
        System.out.println("--- All Appointments ---");
        List<Appointment> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment a : appointments) {
            System.out.println(a);
        }
    }

    private void handleMakePayment() {
        System.out.println("--- Make Payment ---");
        int apptId = readInt("Enter Appointment ID: ");
        Appointment appt = appointmentService.getAppointmentById(apptId);

        System.out.printf("Fee Due: $%.2f for Dr. %s%n", appt.getConsultationFee(), appt.getDoctor().getName());
        System.out.println("Choose Payment Method:");
        System.out.println("  1. UPI");
        System.out.println("  2. Credit / Debit Card");
        System.out.println("  3. Cash / Counter");

        int pChoice = readInt("Select payment option (1-3): ");
        PaymentGateway gateway;

        switch (pChoice) {
            case 1 -> {
                String upiId = readString("Enter UPI ID (e.g. name@okhdfcbank): ");
                gateway = new UpiPayment(upiId);
            }
            case 2 -> {
                String cardNo = readString("Enter 16-digit Card Number: ");
                String holder = readString("Enter Cardholder Name: ");
                String cvv = readString("Enter 3-digit CVV: ");
                gateway = new CardPayment(cardNo, holder, cvv);
            }
            case 3 -> {
                String receipt = readString("Enter counter receipt / cash memo number: ");
                gateway = new CashPayment(receipt);
            }
            default -> {
                System.out.println("Invalid payment method.");
                return;
            }
        }

        // Process payment polymorphically
        Payment payment = paymentService.processAppointmentPayment(apptId, gateway);
        System.out.println(" Payment Complete: " + payment);
    }

    private void handleStartConsultation() {
        System.out.println("--- Start Consultation ---");
        int apptId = readInt("Enter Appointment ID: ");
        Consultation consultation = consultationService.startConsultation(apptId);
        System.out.println(" Consultation active: " + consultation);
    }

    private void handleAddConsultationNotes() {
        System.out.println("--- Add Consultation Notes ---");
        int apptId = readInt("Enter Appointment ID: ");
        Optional<Consultation> opt = consultationService.getConsultationByAppointmentId(apptId);

        Consultation consultation;
        if (opt.isEmpty()) {
            System.out.println("No active consultation found. Starting consultation session...");
            consultation = consultationService.startConsultation(apptId);
        } else {
            consultation = opt.get();
        }

        String note = readString("Enter clinical note: ");
        consultationService.addConsultationNote(consultation.getConsultationId(), note);
        System.out.println(" Note added to Consultation #" + consultation.getConsultationId());

        String completeNow = readString("Complete this consultation now? (y/n): ");
        if (completeNow.equalsIgnoreCase("y")) {
            consultationService.completeConsultation(consultation.getConsultationId());
            System.out.println(" Consultation #" + consultation.getConsultationId() + " marked COMPLETED.");
        }
    }

    private void handleCreatePrescription() {
        System.out.println("--- Create Prescription ---");
        int apptId = readInt("Enter Appointment ID: ");
        String advice = readString("Enter clinical advice / instructions: ");

        Prescription prescription = prescriptionService.createPrescription(apptId, advice);
        System.out.println(" Prescription created with ID #" + prescription.getPrescriptionId());

        boolean addMore = true;
        while (addMore) {
            String med = readString("Add medicine? (y/n): ");
            if (!med.equalsIgnoreCase("y")) {
                addMore = false;
                break;
            }
            String medName = readString("  Medicine Name: ");
            String dosage = readString("  Dosage (e.g. 500mg): ");
            String freq = readString("  Frequency (e.g. Twice daily after meals): ");
            int duration = readInt("  Duration in days: ");

            PrescriptionItem item = new PrescriptionItem(medName, dosage, freq, duration);
            prescriptionService.addMedicine(prescription.getPrescriptionId(), item);
            System.out.println("  Item added.");
        }

        System.out.println("\nGenerated Prescription:");
        System.out.println(prescription.getFormattedPrescription());
    }

    private void handleViewPrescription() {
        System.out.println("--- View Prescription ---");
        System.out.println("1. Find by Appointment ID");
        System.out.println("2. Find by Patient ID");
        int choice = readInt("Select option (1/2): ");

        if (choice == 1) {
            int apptId = readInt("Enter Appointment ID: ");
            Optional<Prescription> opt = prescriptionService.getPrescriptionByAppointmentId(apptId);
            if (opt.isEmpty()) {
                System.out.println("No prescription found for Appointment #" + apptId);
            } else {
                System.out.println(opt.get().getFormattedPrescription());
            }
        } else if (choice == 2) {
            int patientId = readInt("Enter Patient ID: ");
            List<Prescription> list = prescriptionService.getPrescriptionsByPatientId(patientId);
            if (list.isEmpty()) {
                System.out.println("No prescriptions found for Patient #" + patientId);
            } else {
                for (Prescription p : list) {
                    System.out.println(p.getFormattedPrescription());
                    System.out.println();
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void handleViewAppointmentHistory() {
        System.out.println("--- Appointment History ---");
        System.out.println("1. View Patient Appointment History");
        System.out.println("2. View Doctor Appointment History");
        int choice = readInt("Select option (1/2): ");

        if (choice == 1) {
            int patientId = readInt("Enter Patient ID: ");
            List<Appointment> list = appointmentService.getAppointmentsByPatient(patientId);
            if (list.isEmpty()) {
                System.out.println("No appointment history for Patient #" + patientId);
            } else {
                for (Appointment a : list) {
                    System.out.println(a);
                }
            }
        } else if (choice == 2) {
            int doctorId = readInt("Enter Doctor ID: ");
            List<Appointment> list = appointmentService.getAppointmentsByDoctor(doctorId);
            if (list.isEmpty()) {
                System.out.println("No appointment history for Doctor #" + doctorId);
            } else {
                for (Appointment a : list) {
                    System.out.println(a);
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }
}
