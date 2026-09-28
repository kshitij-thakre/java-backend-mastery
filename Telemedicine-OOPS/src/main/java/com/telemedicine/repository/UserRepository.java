package com.telemedicine.repository;

import com.telemedicine.model.Doctor;
import com.telemedicine.model.Patient;
import com.telemedicine.model.Specialization;
import com.telemedicine.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Specialized repository interface for User management.
 * Demonstrates Interface Inheritance (extends Repository<User, Integer>).
 */
public interface UserRepository extends Repository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<Patient> findAllPatients();

    List<Doctor> findAllDoctors();

    List<Doctor> findDoctorsBySpecialization(Specialization specialization);
}
