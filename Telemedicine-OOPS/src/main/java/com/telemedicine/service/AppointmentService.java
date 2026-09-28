package com.telemedicine.service;

import com.telemedicine.model.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(int patientId, int doctorId, String timeSlot);

    Appointment cancelAppointment(int appointmentId);

    Appointment getAppointmentById(int appointmentId);

    List<Appointment> getAllAppointments();

    List<Appointment> getAppointmentsByPatient(int patientId);

    List<Appointment> getAppointmentsByDoctor(int doctorId);
}
