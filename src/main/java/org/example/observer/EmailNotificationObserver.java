package org.example.observer;

import org.example.domain.entity.User;

public class EmailNotificationObserver implements Observer {

    
    @Override
    public void notify(User user, String message) {
        System.out.println("[EMAIL Notification] To: " + user.getEmail() + " | Content: " + message);
    }
}
