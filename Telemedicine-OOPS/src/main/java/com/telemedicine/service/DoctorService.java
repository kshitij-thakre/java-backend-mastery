package com.telemedicine.service;

import com.telemedicine.model.Doctor;
import com.telemedicine.model.Specialization;

import java.util.List;
import java.util.Set;

public interface DoctorService {

    void addAvailableSlot(int doctorId, String slot);

    Set<String> getAvailableSlots(int doctorId);

    List<Doctor> getDoctorsBySpecialization(Specialization specialization);

    boolean isSlotAvailable(int doctorId, String slot);
}
