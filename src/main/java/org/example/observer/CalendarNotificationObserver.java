package org.example.observer;

import org.example.domain.entity.User;

public class CalendarNotificationObserver implements Observer {

    @Override
    public void notify(User user, String message) {
        System.out.println("[CALENDAR] User: " + user.getName() + " | Syncing event: " + message);
    }
}
