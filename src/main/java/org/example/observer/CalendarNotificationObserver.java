package org.example.observer;

import org.example.domain.entity.User;

/**
 * Calendar notification channel.
 * Simulates syncing a reminder to a user's calendar.
 */
public class CalendarNotificationObserver implements Observer {

    /**
     * Sycs the appointment to the user's calendar.
     *
     * @param user    the recipient
     * @param message the message content
     */
    @Override
    public void notify(User user, String message) {
        System.out.println("[CALENDAR] User: " + user.getName() + " | Syncing event: " + message);
    }
}
