package com.telemedicine.repository;

import com.telemedicine.model.Appointment;
import com.telemedicine.model.AppointmentStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository for Appointments.
 */
public class InMemoryAppointmentRepository implements AppointmentRepository {

    private final Map<Integer, Appointment> appointmentStorage = new LinkedHashMap<>();

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null");
        }
        appointmentStorage.put(appointment.getAppointmentId(), appointment);
        return appointment;
    }

    @Override
    public Optional<Appointment> findById(Integer id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(appointmentStorage.get(id));
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointmentStorage.values());
    }

    @Override
    public boolean existsById(Integer id) {
        return id != null && appointmentStorage.containsKey(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        return appointmentStorage.remove(id) != null;
    }

    @Override
    public List<Appointment> findByPatientId(int patientId) {
        List<Appointment> list = new ArrayList<>();
        for (Appointment appt : appointmentStorage.values()) {
            if (appt.getPatient().getId() == patientId) {
                list.add(appt);
            }
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Appointment> findByDoctorId(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        for (Appointment appt : appointmentStorage.values()) {
            if (appt.getDoctor().getId() == doctorId) {
                list.add(appt);
            }
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public boolean existsByDoctorIdAndTimeSlot(int doctorId, String timeSlot) {
        if (timeSlot == null) return false;
        return appointmentStorage.values().stream()
                .anyMatch(a -> a.getDoctor().getId() == doctorId
                        && a.getTimeSlot().equalsIgnoreCase(timeSlot.trim())
                        && a.getStatus() != AppointmentStatus.CANCELLED);
    }
}
