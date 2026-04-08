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

    // TODO: Add field: UserRepository userRepository
    // TODO: Add field: User currentUser   (null when no one is logged in)

    /**
     * Constructor — inject the repository.
     *
     * TODO: Assign userRepository to the field.
     *
     * @param userRepository the user data source
     */
    public AuthService(UserRepository userRepository) {
        // TODO: this.userRepository = userRepository;
    }

    /**
     * Attempts to log in with the given credentials (US1.1).
     *
     * TODO:
     *  1. Call userRepository.findByEmail(email)
     *  2. If user is null → throw new IllegalArgumentException("User not found")
     *  3. Compare the provided password with user.getPassword()
     *     (if using hashing later: use BCrypt.checkpw(password, user.getPassword()))
     *  4. If password matches → set currentUser = user, return user
     *  5. If password doesn't match → throw new IllegalArgumentException("Invalid password")
     *
     * @param email    the user's email
     * @param password the plain-text password entered
     * @return the authenticated User
     * @throws IllegalArgumentException if credentials are invalid
     */
    public User login(String email, String password) {
        // TODO: implement login logic
        return null;
    }

    /**
     * Logs out the current user (US1.2).
     *
     * TODO:
     *  - Set currentUser = null
     *
     * After this, any action requiring login must call login() again.
     */
    public void logout() {
        // TODO: currentUser = null;
    }

    /**
     * Returns the currently logged-in user.
     *
     * TODO: return currentUser;
     *
     * @return the logged-in User, or null if nobody is logged in
     */
    public User getCurrentUser() {
        // TODO: return currentUser;
        return null;
    }

    /**
     * Checks whether any user is currently logged in.
     *
     * TODO: return currentUser != null;
     *
     * @return true if a user is logged in
     */
    public boolean isLoggedIn() {
        // TODO: return currentUser != null;
        return false;
    }

    /**
     * Checks whether the currently logged-in user is an administrator.
     *
     * TODO:
     *  - Check if currentUser is not null
     *  - Return currentUser.getRole().equals("ADMIN")
     *
     * @return true if the current user is an admin
     */
    public boolean isAdmin() {
        // TODO: implement
        return false;
    }
}
