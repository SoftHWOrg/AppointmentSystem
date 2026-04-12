package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.observer.NotificationPublisher;
import org.example.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ReminderService implements NotificationPublisher {

    private final List<Observer> observers;

    public ReminderService() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
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

    /** Crafts a detailed reminder message and triggers all observers. */
    public void sendReminder(Appointment appointment) {
        if (appointment == null || appointment.getUser() == null) return;

        String msg = String.format("Reminder: Your %s appointment [#%d] is on %s at %s. Status: [%s]",
                appointment.getType().name().replace("_", " "),
                appointment.getId(),
                appointment.getTimeSlot().getDate(),
                appointment.getTimeSlot().getStartTime(),
                appointment.getStatus());

        notifyAllObservers(appointment.getUser(), msg);
    }

    /** Generic message dispatcher. */
    public void sendReminder(User user, String message) {
        if (user != null) {
            notifyAllObservers(user, message);
        }
    }
}
