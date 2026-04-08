package org.example.domain.entity;

/**
 * Represents a regular user of the appointment system.
 * Administrators extend this class.
 *
 * @author
 * @version 1.0
 */
public class User {

    // TODO: Add field: int id
    // TODO: Add field: String name
    // TODO: Add field: String email
    // TODO: Add field: String password  (store hashed)
    // TODO: Add field: String role      (value: "USER" or "ADMIN")

    /**
     * Default constructor.
     * TODO: Leave empty or initialize fields to defaults.
     */
    public User() {
        // TODO: implement if needed
    }

    /**
     * Full constructor.
     *
     * TODO: Add parameters (id, name, email, password, role)
     *       and assign each to its field.
     *
     * @param id       unique user ID from the database
     * @param name     full name of the user
     * @param email    user email (used for login)
     * @param password hashed password
     * @param role     "USER" or "ADMIN"
     */
    public User(int id, String name, String email, String password, String role) {
        // TODO: assign all parameters to fields
    }

    // TODO: Add getters and setters for all fields:
    //       getId(), getName(), getEmail(), getPassword(), getRole()
    //       setId(), setName(), setEmail(), setPassword(), setRole()

    /**
     * TODO: Override toString() to return a readable summary of the user,
     *       e.g. "User{id=1, name='John', email='john@email.com', role='USER'}"
     */
    @Override
    public String toString() {
        // TODO: implement
        return "";
    }
}
