package org.example.observer;

import org.example.domain.entity.User;

/**
 * Sends appointment notifications via Calendar invite.
 * Implements the Observer Pattern (Sprint 3 — US3.1).
 *
 * In test mode: Mockito will mock this class to verify notify() was called.
 *
 * @author
 * @version 1.0
 */
public class CalendarNotificationObserver implements Observer {

    /**
     * Sends a calendar notification/invite to the user.
     *
     * TODO:
     *  1. Get user's email: user.getEmail()
     *  2. Print or log: "CALENDAR invite sent to <email>: <message>"
     *  3. In real implementation: generate an .ics file or integrate with Google Calendar API
     *
     * @param user    the user to notify
     * @param message the reminder message content
     */
    @Override
    public void notify(User user, String message) {
        // TODO: implement calendar notification
        // For now: System.out.println("CALENDAR invite to " + user.getEmail() + ": " + message);
    }
}
