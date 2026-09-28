package com.telemedicine.notification;

import com.telemedicine.model.User;

public class EmailNotificationService implements NotificationService {

    @Override
    public void sendNotification(User recipient, String message) {
        if (recipient == null) return;
        System.out.printf("[EMAIL -> %s (%s)] %s%n",
                recipient.getName(), recipient.getEmail(), message);
    }

    @Override
    public String getChannelName() {
        return "EMAIL";
    }
}
