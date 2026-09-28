package com.telemedicine.service;

import com.telemedicine.model.Prescription;
import com.telemedicine.model.PrescriptionItem;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {

    Prescription createPrescription(int appointmentId, String advice);

    Prescription addMedicine(int prescriptionId, PrescriptionItem item);

    Prescription getPrescriptionById(int prescriptionId);

    Optional<Prescription> getPrescriptionByAppointmentId(int appointmentId);

    List<Prescription> getPrescriptionsByPatientId(int patientId);
}
