package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.observer.NotificationPublisher;
import org.example.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for managing notifications.
 * Acts as the Publisher in the Observer pattern.
 */
public class ReminderService implements NotificationPublisher {

    private final List<Observer> observers;

    public ReminderService() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
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
    }
}
