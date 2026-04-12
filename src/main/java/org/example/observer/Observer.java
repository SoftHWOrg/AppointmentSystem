package org.example.observer;

import org.example.domain.entity.User;

public interface Observer {

    void notify(User user, String message);
}
