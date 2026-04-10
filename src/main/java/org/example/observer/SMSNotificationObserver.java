package org.example.observer;

import org.example.domain.entity.User;

/**
 * SMS notification channel.
 * Simulates sending a reminder via SMS.
 */
public class SMSNotificationObserver implements Observer {

    /**
     * Sends an SMS notification to the user.
     *
     * @param user    the recipient
     * @param message the message content
     */
    @Override
    public void notify(User user, String message) {
        System.out.println("[SMS] To: " + user.getName() + " | Message: " + message);
    }
}
