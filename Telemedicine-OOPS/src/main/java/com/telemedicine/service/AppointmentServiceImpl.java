package com.telemedicine.service;

import com.telemedicine.exception.AppointmentNotFoundException;
import com.telemedicine.exception.DoctorNotAvailableException;
import com.telemedicine.model.Appointment;
import com.telemedicine.model.Doctor;
import com.telemedicine.model.Patient;
import com.telemedicine.notification.NotificationDispatcher;
import com.telemedicine.repository.AppointmentRepository;
import com.telemedicine.util.IdGenerator;

import java.util.List;

/**
 * Service implementation for Appointment lifecycle.
 * Demonstrates:
 * - Loose Coupling
 * - Invariant Protection (preventing double booking, validating slot availability)
 * - Domain Events & Notification Dispatch
 */
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final NotificationDispatcher notificationDispatcher;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  UserService userService,
                                  NotificationDispatcher notificationDispatcher) {
        if (appointmentRepository == null || userService == null || notificationDispatcher == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.notificationDispatcher = notificationDispatcher;
    }

    @Override
    public Appointment bookAppointment(int patientId, int doctorId, String timeSlot) {
        Patient patient = userService.getPatientById(patientId);
        Doctor doctor = userService.getDoctorById(doctorId);

        if (!doctor.isAvailable(timeSlot)) {
            throw new DoctorNotAvailableException("Dr. " + doctor.getName() + " is not available at slot: " + timeSlot);
        }

        if (appointmentRepository.existsByDoctorIdAndTimeSlot(doctorId, timeSlot)) {
            throw new DoctorNotAvailableException("Slot " + timeSlot + " is already booked for Dr. " + doctor.getName());
        }

        int appointmentId = IdGenerator.nextAppointmentId();
        Appointment appointment = new Appointment(appointmentId, patient, doctor, timeSlot);

        // Reserve the slot on the doctor
        doctor.removeAvailableSlot(timeSlot);
        appointmentRepository.save(appointment);

        // Send notifications
        String msgPatient = String.format("Appointment booked! Appt #%d with Dr. %s for %s. Fee: $%.2f",
                appointmentId, doctor.getName(), timeSlot, appointment.getConsultationFee());
        String msgDoctor = String.format("New Appointment #%d scheduled with Patient %s for %s.",
                appointmentId, patient.getName(), timeSlot);

        notificationDispatcher.broadcast(patient, msgPatient);
        notificationDispatcher.broadcast(doctor, msgDoctor);

        return appointment;
    }

    @Override
    public Appointment cancelAppointment(int appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        appointment.cancel();

        // Release slot back to doctor
        Doctor doctor = appointment.getDoctor();
        doctor.addAvailableSlot(appointment.getTimeSlot());

        appointmentRepository.save(appointment);

        // Send cancellation notifications
        String cancelMsg = String.format("Appointment #%d for slot %s has been CANCELLED.",
                appointmentId, appointment.getTimeSlot());
        notificationDispatcher.broadcast(appointment.getPatient(), cancelMsg);
        notificationDispatcher.broadcast(doctor, cancelMsg);

        return appointment;
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId));
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        // verify patient exists
        userService.getPatientById(patientId);
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        // verify doctor exists
        userService.getDoctorById(doctorId);
        return appointmentRepository.findByDoctorId(doctorId);
    }
}
