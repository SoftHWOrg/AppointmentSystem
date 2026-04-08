package org.example.repository;

import org.example.domain.entity.User;

/**
 * Repository interface for User persistence.
 * The PostgreSQL implementation will use JDBC to execute SQL queries.
 *
 * @author
 * @version 1.0
 */
public interface UserRepository {

    /**
     * Saves a new user to the database.
     * TODO: INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)
     *
     * @param user the user to save
     */
    void save(User user);

    /**
     * Finds a user by their email address.
     * Used during login to look up the account.
     * TODO: SELECT * FROM users WHERE email = ?
     *
     * @param email the user's email
     * @return the found User, or null if not found
     */
    User findByEmail(String email);

    /**
     * Finds a user by their ID.
     * TODO: SELECT * FROM users WHERE id = ?
     *
     * @param id the user's ID
     * @return the found User, or null if not found
     */
    User findById(int id);

    /**
     * Updates user information.
     * TODO: UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?
     *
     * @param user the user with updated values
     */
    void update(User user);
}
