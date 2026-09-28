package com.telemedicine.notification;

import com.telemedicine.model.User;

public class SmsNotificationService implements NotificationService {

    @Override
    public void sendNotification(User recipient, String message) {
        if (recipient == null) return;
        System.out.printf("[SMS -> %s (%s)] %s%n",
                recipient.getName(), recipient.getPhone(), message);
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}
