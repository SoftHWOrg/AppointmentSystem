package org.example.observer;

import org.example.domain.entity.User;

/**
 * Sends appointment notifications via SMS.
 * Implements the Observer Pattern (Sprint 3 — US3.1).
 *
 * In test mode: Mockito will mock this class to verify notify() was called.
 *
 * @author
 * @version 1.0
 */
public class SMSNotificationObserver implements Observer {

    /**
     * Sends an SMS notification to the user.
     *
     * TODO:
     *  1. Get user's phone number (you may need to add a phone field to User)
     *  2. Print or log: "SMS sent to <phone>: <message>"
     *  3. In real implementation: integrate with an SMS API (e.g. Twilio)
     *
     * @param user    the user to notify
     * @param message the reminder message content
     */
    @Override
    public void notify(User user, String message) {
        // TODO: implement SMS sending
        // For now: System.out.println("SMS to " + user.getName() + ": " + message);
    }
}
