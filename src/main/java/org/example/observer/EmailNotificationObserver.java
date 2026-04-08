package org.example.observer;

import org.example.domain.entity.User;

/**
 * Sends appointment notifications via Email.
 * Implements the Observer Pattern (Sprint 3 — US3.1).
 *
 * In test mode: Mockito will mock this class to verify notify() was called
 * without actually sending any emails.
 *
 * @author
 * @version 1.0
 */
public class EmailNotificationObserver implements Observer {

    /**
     * Sends an email notification to the user.
     *
     * TODO:
     *  1. Get the user's email: user.getEmail()
     *  2. Print or log: "EMAIL sent to <email>: <message>"
     *  3. In real implementation: integrate with JavaMail or an SMTP client
     *
     * @param user    the user to notify
     * @param message the reminder message content
     */
    @Override
    public void notify(User user, String message) {
        // TODO: implement email sending
        // For now: System.out.println("EMAIL to " + user.getEmail() + ": " + message);
    }
}
