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

    public Administrator() {
        super();
        setRole("ADMIN");
    }

    public Administrator(int id, String name, String email, String password) {
        super(id, name, email, password, "ADMIN");
    }

    /**
     * Admins can manage ALL users' appointments.
     *
     * @param userId the ID of the user whose appointment is being managed
     * @return true always (admin has full access)
     */
    public boolean canManage(int userId) {
        return true;
    }
}
