package com.telemedicine.exception;

public class AppointmentNotFoundException extends TelemedicineException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
