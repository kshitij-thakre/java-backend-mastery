package com.telemedicine.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe unique identifier generator.
 * Demonstrates encapsulation of ID state and static helper behavior.
 */
public final class IdGenerator {

    private static final AtomicInteger USER_ID_COUNTER = new AtomicInteger(100);
    private static final AtomicInteger APPOINTMENT_ID_COUNTER = new AtomicInteger(1000);
    private static final AtomicInteger CONSULTATION_ID_COUNTER = new AtomicInteger(5000);
    private static final AtomicInteger PRESCRIPTION_ID_COUNTER = new AtomicInteger(7000);
    private static final AtomicInteger PAYMENT_ID_COUNTER = new AtomicInteger(9000);

    // Private constructor prevents instantiation (Utility class design pattern)
    private IdGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static int nextUserId() {
        return USER_ID_COUNTER.incrementAndGet();
    }

    public static int nextAppointmentId() {
        return APPOINTMENT_ID_COUNTER.incrementAndGet();
    }

    public static int nextConsultationId() {
        return CONSULTATION_ID_COUNTER.incrementAndGet();
    }

    public static int nextPrescriptionId() {
        return PRESCRIPTION_ID_COUNTER.incrementAndGet();
    }

    public static int nextPaymentId() {
        return PAYMENT_ID_COUNTER.incrementAndGet();
    }
}
