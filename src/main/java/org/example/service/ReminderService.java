package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.observer.Observer;
import org.example.observer.NotificationPublisher;

import java.util.ArrayList;
import java.util.List;

/**
 * Sends appointment reminders and notifications.
 * Covers US3.1 — Send appointment reminders.
 *
 * Implements the Observer Pattern as the Subject/Publisher.
 * Holds a list of Observer channels (Email, SMS, Calendar).
 * Calls notify() on each registered observer when a reminder is needed.
 *
 * In tests: Mockito will mock the Observer objects to verify
 * that notify() was called with the correct arguments.
 *
 * @author
 * @version 1.0
 */
public class ReminderService implements NotificationPublisher {

    // TODO: Add field: List<Observer> observers   (registered notification channels)

    /**
     * Default constructor.
     * TODO: Initialize observers as a new ArrayList.
     */
    public ReminderService() {
        // TODO: this.observers = new ArrayList<>();
    }

    /**
     * Registers a notification channel observer.
     *
     * TODO: observers.add(observer);
     *
     * @param observer the observer to add (Email, SMS, Calendar)
     */
    @Override
    public void registerObserver(Observer observer) {
        // TODO: implement
    }

    /**
     * Removes a notification channel observer.
     *
     * TODO: observers.remove(observer);
     *
     * @param observer the observer to remove
     */
    @Override
    public void removeObserver(Observer observer) {
        // TODO: implement
    }

    /**
     * Notifies all registered observers with a message.
     *
     * TODO:
     *  - Loop through observers
     *  - Call observer.notify(user, message) for each
     *
     * @param user    the user to notify
     * @param message the message to send
     */
    @Override
    public void notifyAllObservers(User user, String message) {
        // TODO: implement loop
    }

    /**
     * Sends a reminder for a specific appointment (US3.1).
     *
     * TODO:
     *  1. Build a message string, e.g.:
     *     "Reminder: You have a " + appointment.getType() + " appointment on "
     *     + appointment.getTimeSlot().getDate() + " at " + appointment.getTimeSlot().getStartTime()
     *  2. Call notifyAllObservers(appointment.getUser(), message)
     *
     * @param appointment the upcoming appointment to remind about
     */
    public void sendReminder(Appointment appointment) {
        // TODO: implement reminder logic
    }

    /**
     * Sends a custom one-off message to a user (used after booking/cancellation).
     *
     * TODO: Call notifyAllObservers(user, message)
     *
     * @param user    the target user
     * @param message the message to send
     */
    public void sendReminder(User user, String message) {
        // TODO: notifyAllObservers(user, message);
    }
}
