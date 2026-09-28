# Telemedicine Appointment & Consultation System

A pure **Java 17 console-based Telemedicine System** built specifically to learn and demonstrate core Object-Oriented Programming (OOP) concepts and SOLID design principles through a realistic, practical domain model.

---

## 📖 Table of Contents
1. [Project Overview](#-project-overview)
2. [Features](#-features)
3. [Architecture & Design](#-architecture--design)
4. [Package Structure](#-package-structure)
5. [OOP Concepts Demonstrated](#-oop-concepts-demonstrated)
6. [How OOP Concepts Are Used in the Project](#-how-oop-concepts-are-used-in-the-project)
7. [How to Run the Application](#-how-to-run-the-application)
8. [Sample Terminal Flow](#-sample-terminal-flow)

---

## 🩺 Project Overview

The Telemedicine System allows patients to find doctors by specialization, book and cancel appointments, make payments through multiple payment channels, participate in medical consultations, receive clinical notes, and view formatted electronic prescriptions.

The application deliberately avoids external frameworks (such as Spring Boot, Hibernate, JPA, or external databases) to emphasize pure Java language constructs, memory models, collections, inheritance hierarchies, polymorphism, and architectural design patterns.

---

## 🚀 Features

1. **User Management**
   - Register and view `Patient`, `Doctor`, and `Admin`.
   - Protect invariants (email format, positive age, positive fees).
2. **Doctor Management**
   - Doctor specializations (`Cardiology`, `Dermatology`, `General Physician`, `Pediatrics`, `Neurology`, `Orthopedics`).
   - Dynamic availability slot management (add, book, release slots).
3. **Appointment Scheduling**
   - Book appointments with specific doctor slots.
   - Guard against double bookings and unlisted slots.
   - Cancel appointments with automatic slot restoration.
4. **Payment Processing**
   - Polymorphic payment gateway support (`UPI`, `Credit/Debit Card`, `Cash/Counter`).
   - Interface segregation for `Refundable` operations.
5. **Consultation Lifecycle**
   - Start consultation sessions linked to appointments.
   - Append live clinical notes and observations.
   - Complete consultation state transitions.
6. **Electronic Prescriptions**
   - Doctors issue formatted prescriptions with clinical advice and composite medication items (`dosage`, `frequency`, `duration`).
   - Patients can view prescriptions by Appointment ID or Patient ID.
7. **Multi-Channel Notifications**
   - Automated event broadcasts across Email, SMS, and Push notification channels upon booking, payment, cancellation, and prescription generation.

---

## 🏗 Architecture & Design

The application follows a clean 3-tier architectural separation of concerns:

```
┌─────────────────────────────────────────────────────────┐
│                    Terminal UI (Main)                   │
└───────────────────────────┬─────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────┐
│                      Service Layer                      │
│ (UserService, AppointmentService, PaymentService, etc.) │
└─────────────┬─────────────────────────────┬─────────────┘
              │                             │
┌─────────────▼───────────────┐ ┌───────────▼─────────────┐
│      Repository Layer       │ │   External Gateways     │
│ (In-Memory Collections/Map) │ │ (Payment, Notification) │
└─────────────────────────────┘ └─────────────────────────┘
```

- **Domain Model**: Core entities (`User`, `Patient`, `Doctor`, `Appointment`, `Consultation`, `Prescription`, `Payment`).
- **Repositories**: Generic CRUD contracts (`Repository<T, ID>`) backed by thread-safe, in-memory Java `Map` and `List` collections.
- **Services**: Business rules, state machine transitions, validation, and domain event dispatching.
- **Gateways**: Strategy implementations for payments and notification delivery.

---

## 📁 Package Structure

```
src/
└── main/
    └── java/
        └── com/
            └── telemedicine/
                ├── Main.java
                ├── exception/
                │   ├── AppointmentNotFoundException.java
                │   ├── DoctorNotAvailableException.java
                │   ├── InvalidAppointmentStateException.java
                │   ├── PaymentFailedException.java
                │   ├── TelemedicineException.java
                │   ├── UserNotFoundException.java
                │   └── ValidationException.java
                ├── model/
                │   ├── Admin.java
                │   ├── Appointment.java
                │   ├── AppointmentStatus.java
                │   ├── Consultation.java
                │   ├── ConsultationStatus.java
                │   ├── Doctor.java
                │   ├── Patient.java
                │   ├── Payment.java
                │   ├── PaymentStatus.java
                │   ├── Prescription.java
                │   ├── PrescriptionItem.java
                │   ├── Specialization.java
                │   ├── User.java
                │   └── UserRole.java
                ├── notification/
                │   ├── EmailNotificationService.java
                │   ├── NotificationDispatcher.java
                │   ├── NotificationService.java
                │   ├── PushNotificationService.java
                │   └── SmsNotificationService.java
                ├── payment/
                │   ├── CardPayment.java
                │   ├── CashPayment.java
                │   ├── PaymentGateway.java
                │   ├── PaymentProcessor.java
                │   ├── Refundable.java
                │   └── UpiPayment.java
                ├── repository/
                │   ├── AppointmentRepository.java
                │   ├── ConsultationRepository.java
                │   ├── InMemoryAppointmentRepository.java
                │   ├── InMemoryConsultationRepository.java
                │   ├── InMemoryPaymentRepository.java
                │   ├── InMemoryPrescriptionRepository.java
                │   ├── InMemoryUserRepository.java
                │   ├── PaymentRepository.java
                │   ├── PrescriptionRepository.java
                │   ├── Repository.java
                │   └── UserRepository.java
                ├── service/
                │   ├── AppointmentService.java
                │   ├── AppointmentServiceImpl.java
                │   ├── ConsultationService.java
                │   ├── ConsultationServiceImpl.java
                │   ├── DoctorService.java
                │   ├── DoctorServiceImpl.java
                │   ├── PaymentService.java
                │   ├── PaymentServiceImpl.java
                │   ├── PrescriptionService.java
                │   ├── PrescriptionServiceImpl.java
                │   ├── UserService.java
                │   └── UserServiceImpl.java
                └── util/
                    ├── DataInitializer.java
                    ├── IdGenerator.java
                    └── ValidationUtil.java
```

---

## 🎯 OOP Concepts Demonstrated

The codebase is mapped to the following OOP concepts:

1. **Classes & Objects** (e.g. `Patient`, `Doctor`, `Appointment`)
2. **Fields & Methods** (e.g. attributes and behaviors encapsulated in entities)
3. **Constructors & Parameterized Constructors** (e.g. `Patient(...)`, `Doctor(...)`)
4. **`this` Keyword** (differentiating instance variables from parameters)
5. **Access Modifiers & Private Fields** (data hiding with `private`, `protected`, `public`)
6. **Getters & Setters** (controlled access to state)
7. **Encapsulation** (defensive copies via `Collections.unmodifiableList`, invariant protection)
8. **Validation & Invariants** (`ValidationUtil` guarding positive fees, non-blank strings, valid email formats)
9. **`final` Fields** (immutable IDs and value objects)
10. **Inheritance (`extends`)** (`Patient extends User`, `Doctor extends User`)
11. **`super` Keyword** (invoking base class constructors and methods)
12. **Constructor Chaining** (`this(...)` calling overloaded constructors)
13. **Method Overriding & `@Override`** (`getDisplayDetails()`, `toString()`)
14. **Upcasting** (referencing `Patient` and `Doctor` as `User`)
15. **Runtime Polymorphism** (dynamic method dispatch on `getDisplayDetails()`, `processPayment()`)
16. **Method Overloading / Compile-time Polymorphism** (`User.updateContact(email)` vs `User.updateContact(email, phone)`)
17. **Abstract Classes & Abstract Methods** (`abstract class User`, `abstract String getDisplayDetails()`)
18. **Interfaces** (`PaymentGateway`, `NotificationService`, `Repository<T, ID>`)
19. **Multiple Interfaces** (`UpiPayment implements PaymentGateway, Refundable`)
20. **Interface Inheritance** (`UserRepository extends Repository<User, Integer>`)
21. **IS-A Relationship** (`Doctor` IS-A `User`)
22. **HAS-A Association** (`Appointment` HAS-A `Patient` and `Doctor`)
23. **HAS-A Aggregation** (`NotificationDispatcher` aggregates `NotificationService` instances)
24. **HAS-A Composition** (`Prescription` composes `PrescriptionItem` objects)
25. **Loose Coupling & High Cohesion** (Service and Repository layers bound via interfaces)
26. **Composition over Inheritance** (`Appointment` delegates to separate status enums and participant objects)
27. **SOLID Principles**:
    - **Single Responsibility Principle (SRP)**: Each service, repository, and model has one clear concern.
    - **Open/Closed Principle (OCP)**: New payment methods or notification channels plug in without modifying existing classes.
    - **Interface Segregation Principle (ISP)**: `Refundable` is separated from `PaymentGateway`.
    - **Dependency Inversion Principle (DIP)**: High-level modules depend on abstractions (`UserRepository`, `PaymentGateway`).

> For a complete symbol-by-symbol code index, see [docs/oop-concepts.md](docs/oop-concepts.md).

---

## 💡 How OOP Concepts Are Used in the Project

### 1. Inheritance & Abstraction: The User Hierarchy
```
           ┌──────────────┐
           │ abstract User│
           └──────┬───────┘
                  │
     ┌────────────┼────────────┐
     │            │            │
┌────▼────┐  ┌────▼───┐   ┌────▼────┐
│ Patient │  │ Doctor │   │  Admin  │
└─────────┘  └────────┘   └─────────┘
```
- `User` encapsulates common fields (`id`, `name`, `email`, `phone`, `role`) and defines an abstract method `getDisplayDetails()`.
- `Patient` introduces specialized attributes (`age`, `bloodGroup`, `medicalHistory`).
- `Doctor` introduces `specialization`, `consultationFee`, `experienceYears`, and `availableSlots`.
- Subclasses override `getDisplayDetails()` to provide custom string representations.

### 2. Strategy & Interfaces: Payment Subsystem
- `PaymentGateway` defines `processPayment(appointmentId, amount)`.
- `Refundable` defines `processRefund(transactionRef, amount)`.
- `UpiPayment` and `CardPayment` implement **both** interfaces.
- `CashPayment` implements **only** `PaymentGateway`, demonstrating **Interface Segregation**.
- `PaymentProcessor` receives any `PaymentGateway` at runtime (**Dependency Inversion & Runtime Polymorphism**).

### 3. Object Relationships: Association vs Aggregation vs Composition
- **Association**: `Appointment` has references to independent `Patient` and `Doctor` objects.
- **Aggregation**: `NotificationDispatcher` contains a collection of independent `NotificationService` instances.
- **Composition**: `Prescription` owns a `List<PrescriptionItem>`. A prescription item only exists in the context of its parent prescription.

---

## 💻 How to Run the Application

### Prerequisites
- **JDK 17** or higher (`javac -version`, `java -version`)

### Compile
From the project root:
```bash
mkdir -p out
javac -d out $(find src/main/java -name "*.java")
```

### Run
```bash
java -cp out com.telemedicine.Main
```

---

## 🖥 Sample Terminal Flow

```text
==================================================
 Welcome to Telemedicine OOP System (Java 17)
 Sample Doctors, Patients & Slots pre-loaded!
==================================================
===== TELEMEDICINE SYSTEM =====
1. Register Patient
2. Register Doctor
3. View Patients
4. View Doctors
5. View Doctor Availability
6. Book Appointment
7. Cancel Appointment
8. View Appointments
9. Make Payment
10. Start Consultation
11. Add Consultation Notes
12. Create Prescription
13. View Prescription
14. View Appointment History
0. Exit
===============================
Enter choice: 3

--- Registered Patients ---
Patient #104: Alice Smith (Age: 29, Blood Group: O+) | Contact: +1-555-0202 | Email: alice.smith@example.com | History Records: 1
Patient #105: Bob Johnson (Age: 45, Blood Group: A+) | Contact: +1-555-0303 | Email: bob.j@example.com | History Records: 1
Patient #106: Charlie Brown (Age: 34, Blood Group: B+) | Contact: +1-555-0404 | Email: charlie.b@example.com | History Records: 0

===== TELEMEDICINE SYSTEM =====
...
Enter choice: 9
--- Make Payment ---
Enter Appointment ID: 1001
Fee Due: $75.00 for Dr. Sarah Connor
Choose Payment Method:
  1. UPI
  2. Credit / Debit Card
  3. Cash / Counter
Select payment option (1-3): 1
Enter UPI ID (e.g. name@okhdfcbank): alice@okhdfcbank
[PaymentGateway: UPI] Processing charge of $75.00 for Appt #1001...
[Payment Successful] Txn Ref: UPI-707825B8 | Status: COMPLETED
[EMAIL -> Alice Smith (alice.smith@example.com)] Payment received: $75.00 for Appt #1001 via UPI (Txn: UPI-707825B8).
[SMS -> Alice Smith (+1-555-0202)] Payment received: $75.00 for Appt #1001 via UPI (Txn: UPI-707825B8).
[PUSH ALERT -> User #104: Alice Smith] Payment received: $75.00 for Appt #1001 via UPI (Txn: UPI-707825B8).
 Payment Complete: Payment #9001 | Appt #1001 | Amount: $75.00 | Method: UPI (alice@okhdfcbank) | Status: COMPLETED | Ref: UPI-707825B8 | Time: 2026-09-28 10:32:29
```
