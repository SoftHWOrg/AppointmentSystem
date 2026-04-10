package org.example.observer;

import org.example.domain.entity.User;

/**
 * Observer interface for the notification system.
 * Implementing classes handle a specific notification channel.
 */
public interface Observer {

    /**
     * Called when a notification needs to be sent.
     *
     * @param user    the recipient of the notification
     * @param message the notification message content
     */
    void notify(User user, String message);
}
