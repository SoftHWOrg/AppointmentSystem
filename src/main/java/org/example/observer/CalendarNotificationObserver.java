package org.example.observer;

import org.example.domain.entity.User;

public class CalendarNotificationObserver implements Observer {

    
    @Override
    public void notify(User user, String message) {
        System.out.println("[CALENDAR Event] Added for: " + user.getName() + " | Details: " + message);
    }
}
