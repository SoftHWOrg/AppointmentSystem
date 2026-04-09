package org.example.repository;

import org.example.domain.entity.User;

public interface UserRepository {

    
    void save(User user);

    
    User findByEmail(String email);

    
    User findById(int id);

    
    void update(User user);
}
