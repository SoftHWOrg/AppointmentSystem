package org.example.observer;

import org.example.domain.entity.User;

/**
 * Email notification channel.
 * Simulates sending a reminder via email.
 */
public class EmailNotificationObserver implements Observer {

    /**
     * Sends an email notification to the user.
     *
     * @param user    the recipient
     * @param message the message content
     */
    @Override
    public void notify(User user, String message) {
        System.out.println("[EMAIL] To: " + user.getEmail() + " | Message: " + message);
    }
}
