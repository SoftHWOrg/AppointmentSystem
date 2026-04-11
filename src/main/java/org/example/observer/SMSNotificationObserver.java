package org.example.observer;

import org.example.domain.entity.User;

public class SMSNotificationObserver implements Observer {

    
    @Override
    public void notify(User user, String message) {
        System.out.println("[SMS Notification] To: " + user.getName() + " | Content: " + message);
    }
}
