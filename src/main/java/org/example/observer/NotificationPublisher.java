package org.example.observer;

import org.example.domain.entity.User;

/**
 * Subject (Publisher) interface in the Observer Pattern.
 * ReminderService implements this to manage the list of observers.
 *
 * @author
 * @version 1.0
 */
public interface NotificationPublisher {

    /**
     * Registers a new observer (notification channel).
     *
     * TODO: Add the observer to the internal list.
     *
     * @param observer the observer to register
     */
    void registerObserver(Observer observer);

    /**
     * Removes an observer from the notification list.
     *
     * TODO: Remove the observer from the internal list.
     *
     * @param observer the observer to remove
     */
    void removeObserver(Observer observer);

    /**
     * Sends a message to all registered observers.
     *
     * TODO: Loop through all observers and call observer.notify(user, message)
     *
     * @param user    the user to notify
     * @param message the message to send
     */
    void notifyAllObservers(User user, String message);
}
