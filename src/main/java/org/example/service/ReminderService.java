package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.observer.NotificationPublisher;
import org.example.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for managing notification observers and sending reminders
 * to users regarding their appointments.
 * Implements the {@link NotificationPublisher} interface to allow observers
 * to subscribe to notification events.
 */
public class ReminderService implements NotificationPublisher {

    /** List of registered observers. */
    private final List<Observer> observers;

    /**
     * Constructs a new ReminderService with an empty list of observers.
     */
    public ReminderService() {
        this.observers = new ArrayList<>();
    }

    /**
     * Registers a new observer to receive notifications.
     * Duplicate observers are not allowed.
     *
     * @param observer the observer to be registered
     */
    @Override
    public void registerObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes a registered observer.
     *
     * @param observer the observer to be removed
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers with a specific message for a user.
     *
     * @param user    the recipient of the notification
     * @param message the content of the notification
     */
    @Override
    public void notifyAllObservers(User user, String message) {
        for (Observer observer : observers) {
            observer.notify(user, message);
        }
    }

    /**
     * Sends a standardized reminder message for a specific appointment.
     * The message includes the appointment type, ID, date, time, and status.
     *
     * @param appointment the appointment to send a reminder for
     */
    public void sendReminder(Appointment appointment) {
        if (appointment == null || appointment.getUser() == null)
            return;

        String msg = String.format("Reminder: Your %s appointment [#%d] is on %s at %s. Status: [%s]",
                appointment.getType().name().replace("_", " "),
                appointment.getId(),
                appointment.getTimeSlot().getDate(),
                appointment.getTimeSlot().getStartTime(),
                appointment.getStatus());

        notifyAllObservers(appointment.getUser(), msg);
    }

    /**
     * Sends a custom reminder message to a specific user.
     *
     * @param user    the recipient of the reminder
     * @param message the content of the reminder
     */
    public void sendReminder(User user, String message) {
        if (user != null) {
            notifyAllObservers(user, message);
        }
    }
}
