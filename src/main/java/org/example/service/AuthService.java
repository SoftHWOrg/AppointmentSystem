package org.example.service;

import org.example.domain.entity.User;
import org.example.repository.UserRepository;

/**
 * Handles administrator and user authentication.
 * Covers US1.1 (login) and US1.2 (logout).
 *
 * @author
 * @version 1.0
 */
public class AuthService {

    private final UserRepository userRepository;
    private User currentUser;

    /**
     * Constructor — inject the repository.
     *
     * @param userRepository the user data source
     */
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Attempts to log in with the given credentials (US1.1).
     * Looks up the user by email, then validates the password.
     *
     * @param email    the user's email
     * @param password the plain-text password entered
     * @return the authenticated User
     * @throws IllegalArgumentException if the email is not found or password is wrong
     */
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        currentUser = user;
        return currentUser;
    }

    /**
     * Logs out the current user (US1.2).
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Returns the currently logged-in user, or null if nobody is logged in.
     *
     * @return the logged-in User, or null
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks whether any user is currently logged in.
     *
     * @return true if a user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Checks whether the currently logged-in user is an administrator.
     *
     * @return true if the current user has the ADMIN role
     */
    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }
}
