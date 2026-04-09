package org.example.service;

import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.observer.Observer;
import org.example.observer.NotificationPublisher;

import java.util.ArrayList;
import java.util.List;

public class ReminderService implements NotificationPublisher {

    
    public ReminderService() {

    }

    
    @Override
    public void registerObserver(Observer observer) {

    }

    
    @Override
    public void removeObserver(Observer observer) {

    }

    
    @Override
    public void notifyAllObservers(User user, String message) {

    }

    
    public void sendReminder(Appointment appointment) {

    }

    
    public void sendReminder(User user, String message) {

    }
}
