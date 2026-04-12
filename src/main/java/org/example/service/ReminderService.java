package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
<<<<<<< HEAD
import org.example.observer.NotificationPublisher;
import org.example.observer.Observer;
=======
import org.example.observer.Observer;
import org.example.observer.NotificationPublisher;
>>>>>>> origin/ibrahimbranch

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
/**
 * Service responsible for managing notifications.
 * Acts as the Publisher in the Observer pattern.
 */
=======
>>>>>>> origin/ibrahimbranch
public class ReminderService implements NotificationPublisher {

    private final List<Observer> observers;

    public ReminderService() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
<<<<<<< HEAD
        if (!observers.contains(observer)) {
=======
        if (observer != null && !observers.contains(observer)) {
>>>>>>> origin/ibrahimbranch
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers(User user, String message) {
        for (Observer observer : observers) {
            observer.notify(user, message);
        }
    }

<<<<<<< HEAD
    /**
     * Sends a reminder for a specific appointment.
     *
     * @param appointment The appointment to send a reminder for
     */
    public void sendReminder(Appointment appointment) {
        if (appointment == null || appointment.getUser() == null) return;
        
        String action = switch (appointment.getStatus()) {
            case CONFIRMED -> "confirmed";
            case CANCELLED -> "CANCELLED";
            case PENDING   -> "pending";
        };

        String message = String.format("Appointment status: [%s]. Date: %s at %s. (ID: %d)",
                action,
                appointment.getTimeSlot().getDate(),
                appointment.getTimeSlot().getStartTime(),
                appointment.getId());
                
        notifyAllObservers(appointment.getUser(), message);
=======
    /** Crafts a detailed reminder message and triggers all observers. */
    public void sendReminder(Appointment appointment) {
        if (appointment == null || appointment.getUser() == null) return;

        String msg = String.format("Reminder: Your %s appointment [#%d] is on %s at %s.",
                appointment.getType().name().replace("_", " "),
                appointment.getId(),
                appointment.getTimeSlot().getDate(),
                appointment.getTimeSlot().getStartTime());

        notifyAllObservers(appointment.getUser(), msg);
    }

    /** Generic message dispatcher. */
    public void sendReminder(User user, String message) {
        notifyAllObservers(user, message);
>>>>>>> origin/ibrahimbranch
    }
}
