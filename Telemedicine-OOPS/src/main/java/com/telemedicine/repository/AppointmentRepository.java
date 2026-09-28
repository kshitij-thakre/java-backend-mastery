package com.telemedicine.repository;

import com.telemedicine.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends Repository<Appointment, Integer> {

    List<Appointment> findByPatientId(int patientId);

    List<Appointment> findByDoctorId(int doctorId);

    boolean existsByDoctorIdAndTimeSlot(int doctorId, String timeSlot);
}
