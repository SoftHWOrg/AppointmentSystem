package org.example.observer;

import org.example.domain.entity.User;

/**
 * Observer Pattern interface for notification channels.
 * Each concrete observer handles one delivery channel (Email, SMS, Calendar).
 *
 * How it works:
 *  - ReminderService (the Subject/Publisher) holds a List<Observer>
 *  - When a reminder needs to be sent, it calls notify() on all registered observers
 *  - Each observer delivers the message through its own channel
 *
 * Adding a new channel = create a new class implementing this interface.
 *
 * @author
 * @version 1.0
 */
public interface Observer {

    /**
     * Sends a notification message to the given user.
     *
     * @param user    the user to notify
     * @param message the message content to send
     */
    void notify(User user, String message);
}
