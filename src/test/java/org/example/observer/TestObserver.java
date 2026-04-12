package org.example.observer;

import org.example.domain.entity.User;
import java.util.ArrayList;
import java.util.List;

public class TestObserver implements Observer {
    public List<Notification> notifications = new ArrayList<>();

    @Override
    public void notify(User user, String message) {
        notifications.add(new Notification(user, message));
    }

    public int getCount() {
        return notifications.size();
    }

    public static class Notification {
        public User user;
        public String message;

        public Notification(User user, String message) {
            this.user = user;
            this.message = message;
        }
    }
}
