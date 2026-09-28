package com.telemedicine.exception;

/**
 * Base unchecked exception for the Telemedicine application.
 * Demonstrates inheritance from RuntimeException and custom exception hierarchy.
 */
public class TelemedicineException extends RuntimeException {

    public TelemedicineException(String message) {
        super(message);
    }

    public TelemedicineException(String message, Throwable cause) {
        super(message, cause);
    }
}
