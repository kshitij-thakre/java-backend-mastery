package com.telemedicine.service;

import com.telemedicine.exception.TelemedicineException;
import com.telemedicine.model.Appointment;
import com.telemedicine.model.Prescription;
import com.telemedicine.model.PrescriptionItem;
import com.telemedicine.notification.NotificationDispatcher;
import com.telemedicine.repository.PrescriptionRepository;
import com.telemedicine.util.IdGenerator;

import java.util.List;
import java.util.Optional;

public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentService appointmentService;
    private final NotificationDispatcher notificationDispatcher;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   AppointmentService appointmentService,
                                   NotificationDispatcher notificationDispatcher) {
        if (prescriptionRepository == null || appointmentService == null || notificationDispatcher == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentService = appointmentService;
        this.notificationDispatcher = notificationDispatcher;
    }

    @Override
    public Prescription createPrescription(int appointmentId, String advice) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        int prescriptionId = IdGenerator.nextPrescriptionId();

        Prescription prescription = new Prescription(
                prescriptionId,
                appointmentId,
                appointment.getDoctor(),
                appointment.getPatient(),
                advice
        );

        prescriptionRepository.save(prescription);

        String rxMsg = String.format("A new prescription #%d has been issued by Dr. %s for Appt #%d.",
                prescriptionId, appointment.getDoctor().getName(), appointmentId);
        notificationDispatcher.broadcast(appointment.getPatient(), rxMsg);

        return prescription;
    }

    @Override
    public Prescription addMedicine(int prescriptionId, PrescriptionItem item) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        prescription.addItem(item);
        prescriptionRepository.save(prescription);
        return prescription;
    }

    @Override
    public Prescription getPrescriptionById(int prescriptionId) {
        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new TelemedicineException("Prescription not found with ID: " + prescriptionId));
    }

    @Override
    public Optional<Prescription> getPrescriptionByAppointmentId(int appointmentId) {
        return prescriptionRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(int patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }
}
