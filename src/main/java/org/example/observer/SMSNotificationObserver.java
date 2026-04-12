package org.example.observer;

import org.example.domain.entity.User;

public class SMSNotificationObserver implements Observer {

    @Override
    public void notify(User user, String message) {
        System.out.println("[SMS] To: " + user.getName() + " | Message: " + message);
    }
}
