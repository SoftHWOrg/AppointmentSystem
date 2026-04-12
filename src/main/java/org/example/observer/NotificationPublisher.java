package org.example.observer;

import org.example.domain.entity.User;

public interface NotificationPublisher {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyAllObservers(User user, String message);
}
