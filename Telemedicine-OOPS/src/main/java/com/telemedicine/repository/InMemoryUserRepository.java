package com.telemedicine.repository;

import com.telemedicine.model.Doctor;
import com.telemedicine.model.Patient;
import com.telemedicine.model.Specialization;
import com.telemedicine.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory repository implementing UserRepository using Java Collections.
 * Demonstrates:
 * - Upcasting: Storing Patient, Doctor, Admin as base type User in Map<Integer, User>
 * - Downcasting safely via instanceof / Pattern Matching
 * - Encapsulation of data storage
 */
public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, User> userStorage = new LinkedHashMap<>();

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        userStorage.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Integer id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(userStorage.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStorage.values());
    }

    @Override
    public boolean existsById(Integer id) {
        return id != null && userStorage.containsKey(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        return userStorage.remove(id) != null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return userStorage.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }

    @Override
    public List<Patient> findAllPatients() {
        List<Patient> patients = new ArrayList<>();
        for (User user : userStorage.values()) {
            if (user instanceof Patient patient) {
                patients.add(patient);
            }
        }
        return Collections.unmodifiableList(patients);
    }

    @Override
    public List<Doctor> findAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        for (User user : userStorage.values()) {
            if (user instanceof Doctor doctor) {
                doctors.add(doctor);
            }
        }
        return Collections.unmodifiableList(doctors);
    }

    @Override
    public List<Doctor> findDoctorsBySpecialization(Specialization specialization) {
        if (specialization == null) return Collections.emptyList();
        List<Doctor> matches = new ArrayList<>();
        for (User user : userStorage.values()) {
            if (user instanceof Doctor doctor && doctor.getSpecialization() == specialization) {
                matches.add(doctor);
            }
        }
        return Collections.unmodifiableList(matches);
    }
}
