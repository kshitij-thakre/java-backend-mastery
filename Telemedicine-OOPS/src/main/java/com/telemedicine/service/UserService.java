package com.telemedicine.service;

import com.telemedicine.model.Doctor;
import com.telemedicine.model.Patient;
import com.telemedicine.model.Specialization;
import com.telemedicine.model.User;

import java.util.List;

public interface UserService {

    Patient registerPatient(String name, String email, String phone, int age, String bloodGroup);

    Doctor registerDoctor(String name, String email, String phone,
                          Specialization specialization, double fee, int experience);

    List<Patient> getAllPatients();

    List<Doctor> getAllDoctors();

    Patient getPatientById(int patientId);

    Doctor getDoctorById(int doctorId);

    User getUserById(int userId);
}
