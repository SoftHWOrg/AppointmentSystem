package org.example.domain.entity;

/**
 * Represents an administrator user with elevated privileges.
 * Extends {@link User} — inherits all user fields.
 * Only administrators can manage (modify/cancel) any reservation (US4.2).
 *
 * @author
 * @version 1.0
 */
public class Administrator extends User {

    /**
     * Default constructor.
     * TODO: Call super() and set role to "ADMIN".
     */
    public Administrator() {
        // TODO: super(); setRole("ADMIN");
    }

    /**
     * Full constructor.
     *
     * TODO: Call the parent User constructor with the provided values,
     *       and force role = "ADMIN".
     *
     * @param id       unique admin ID from the database
     * @param name     full name of the administrator
     * @param email    admin email (used for login)
     * @param password hashed password
     */
    public Administrator(int id, String name, String email, String password) {
        // TODO: super(id, name, email, password, "ADMIN");
    }

    /**
     * Checks whether this administrator has permission to manage a given user's appointments.
     * Admins can manage ALL users' appointments (US4.2).
     *
     * TODO:
     *  - Always return true — admins have full access
     *
     * @param userId the ID of the user whose appointment is being managed
     * @return true always (admin has full access)
     */
    public boolean canManage(int userId) {
        // TODO: return true;
        return false;
    }
}
