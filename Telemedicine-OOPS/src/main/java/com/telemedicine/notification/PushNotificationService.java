package com.telemedicine.notification;

import com.telemedicine.model.User;

public class PushNotificationService implements NotificationService {

    @Override
    public void sendNotification(User recipient, String message) {
        if (recipient == null) return;
        System.out.printf("[PUSH ALERT -> User #%d: %s] %s%n",
                recipient.getId(), recipient.getName(), message);
    }

    @Override
    public String getChannelName() {
        return "PUSH";
    }
}
