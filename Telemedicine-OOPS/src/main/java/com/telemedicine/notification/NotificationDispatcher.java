package com.telemedicine.notification;

import com.telemedicine.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite notification dispatcher.
 * Demonstrates:
 * - Aggregation (HAS-A collection of NotificationService instances)
 * - Loose Coupling
 * - Polymorphism (iterates over NotificationService interface implementations)
 */
public class NotificationDispatcher {

    private final List<NotificationService> channels = new ArrayList<>();

    public NotificationDispatcher() {
        // Default channels
        channels.add(new EmailNotificationService());
        channels.add(new SmsNotificationService());
        channels.add(new PushNotificationService());
    }

    public void registerChannel(NotificationService service) {
        if (service != null && !channels.contains(service)) {
            channels.add(service);
        }
    }

    public void broadcast(User recipient, String message) {
        if (recipient == null) return;
        for (NotificationService channel : channels) {
            channel.sendNotification(recipient, message);
        }
    }
}
