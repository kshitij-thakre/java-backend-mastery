package com.telemedicine.service;

import com.telemedicine.exception.UserNotFoundException;
import com.telemedicine.exception.ValidationException;
import com.telemedicine.model.Doctor;
import com.telemedicine.model.Patient;
import com.telemedicine.model.Specialization;
import com.telemedicine.model.User;
import com.telemedicine.repository.UserRepository;
import com.telemedicine.util.IdGenerator;

import java.util.List;

/**
 * Service implementation for user lifecycle operations.
 * Demonstrates:
 * - Dependency Inversion Principle (depends on UserRepository interface)
 * - Single Responsibility Principle
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("UserRepository cannot be null");
        }
        this.userRepository = userRepository;
    }

    @Override
    public Patient registerPatient(String name, String email, String phone, int age, String bloodGroup) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ValidationException("User already exists with email: " + email);
        }
        int id = IdGenerator.nextUserId();
        Patient patient = new Patient(id, name, email, phone, age, bloodGroup);
        userRepository.save(patient);
        return patient;
    }

    @Override
    public Doctor registerDoctor(String name, String email, String phone,
                                 Specialization specialization, double fee, int experience) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ValidationException("User already exists with email: " + email);
        }
        int id = IdGenerator.nextUserId();
        Doctor doctor = new Doctor(id, name, email, phone, specialization, fee, experience);
        userRepository.save(doctor);
        return doctor;
    }

    @Override
    public List<Patient> getAllPatients() {
        return userRepository.findAllPatients();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return userRepository.findAllDoctors();
    }

    @Override
    public Patient getPatientById(int patientId) {
        User user = userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("Patient not found with ID: " + patientId));
        if (user instanceof Patient patient) {
            return patient;
        }
        throw new UserNotFoundException("User ID " + patientId + " is not a Patient.");
    }

    @Override
    public Doctor getDoctorById(int doctorId) {
        User user = userRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException("Doctor not found with ID: " + doctorId));
        if (user instanceof Doctor doctor) {
            return doctor;
        }
        throw new UserNotFoundException("User ID " + doctorId + " is not a Doctor.");
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
}
