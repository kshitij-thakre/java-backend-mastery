package com.telemedicine.service;

import com.telemedicine.exception.DoctorNotAvailableException;
import com.telemedicine.model.Doctor;
import com.telemedicine.model.Specialization;
import com.telemedicine.repository.UserRepository;

import java.util.List;
import java.util.Set;

public class DoctorServiceImpl implements DoctorService {

    private final UserRepository userRepository;
    private final UserService userService;

    public DoctorServiceImpl(UserRepository userRepository, UserService userService) {
        if (userRepository == null || userService == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void addAvailableSlot(int doctorId, String slot) {
        Doctor doctor = userService.getDoctorById(doctorId);
        doctor.addAvailableSlot(slot);
        userRepository.save(doctor);
    }

    @Override
    public Set<String> getAvailableSlots(int doctorId) {
        Doctor doctor = userService.getDoctorById(doctorId);
        return doctor.getAvailableSlots();
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(Specialization specialization) {
        return userRepository.findDoctorsBySpecialization(specialization);
    }

    @Override
    public boolean isSlotAvailable(int doctorId, String slot) {
        Doctor doctor = userService.getDoctorById(doctorId);
        return doctor.isAvailable(slot);
    }
}
