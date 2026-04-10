package org.example.observer;

import org.example.domain.entity.User;

/**
 * Publisher interface for the notification system.
 * Manages a list of observers and broadcasts notifications to all of them.
 */
public interface NotificationPublisher {

    /**
     * Registers a new observer to receive notifications.
     *
     * @param observer the observer to register
     */
    void registerObserver(Observer observer);

    /**
     * Removes a previously registered observer.
     *
     * @param observer the observer to remove
     */
    void removeObserver(Observer observer);

    /**
     * Sends a notification message to all registered observers.
     *
     * @param user    the recipient user
     * @param message the message to send
     */
    void notifyAllObservers(User user, String message);
}
