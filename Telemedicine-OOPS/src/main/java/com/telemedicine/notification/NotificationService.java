package com.telemedicine.notification;

import com.telemedicine.model.User;

/**
 * Interface representing a notification channel.
 * Demonstrates:
 * - Interface-based Polymorphism
 * - Single Responsibility Principle (SRP)
 */
public interface NotificationService {

    void sendNotification(User recipient, String message);

    String getChannelName();
}
