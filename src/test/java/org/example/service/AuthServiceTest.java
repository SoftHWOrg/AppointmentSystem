package org.example.service;

import org.example.domain.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link AuthService}.
 * Uses Mockito to mock UserRepository — no real DB needed.
 *
 * @author
 * @version 1.0
 */
class AuthServiceTest {

    // TODO: Add field: UserRepository mockUserRepo (Mockito mock)
    // TODO: Add field: AuthService authService

    /**
     * Sets up fresh mocks before each test.
     *
     * TODO:
     * mockUserRepo = Mockito.mock(UserRepository.class);
     * authService = new AuthService(mockUserRepo);
     */
    @BeforeEach
    void setUp() {
        // TODO: initialize mocks and authService
    }

    /**
     * US1.1 — Valid credentials should return the user and set currentUser.
     *
     * TODO:
     * 1. Create a fake User: new User(1, "John", "john@email.com", "pass123",
     * "USER")
     * 2. Stub the mock:
     * when(mockUserRepo.findByEmail("john@email.com")).thenReturn(fakeUser)
     * 3. Call: User result = authService.login("john@email.com", "pass123")
     * 4. Assert: assertNotNull(result)
     * 5. Assert: assertEquals("John", result.getName())
     * 6. Assert: assertTrue(authService.isLoggedIn())
     */
    @Test
    void testLogin_validCredentials_returnsUser() {
        // TODO: implement test
    }

    /**
     * US1.1 — Invalid password should throw IllegalArgumentException.
     *
     * TODO:
     * 1. Stub mockUserRepo to return a user with password "correct"
     * 2. Call authService.login(email, "wrong") inside assertThrows(...)
     * 3. assertThrows(IllegalArgumentException.class, () -> authService.login(...))
     */
    @Test
    void testLogin_invalidPassword_throwsException() {
        // TODO: implement test
    }

    /**
     * US1.1 — Unknown email should throw IllegalArgumentException.
     *
     * TODO:
     * 1. Stub: when(mockUserRepo.findByEmail("unknown@email.com")).thenReturn(null)
     * 2. assertThrows(IllegalArgumentException.class, () ->
     * authService.login("unknown@email.com", "any"))
     */
    @Test
    void testLogin_unknownEmail_throwsException() {
        // TODO: implement test
    }

    /**
     * US1.2 — After logout, isLoggedIn() should return false.
     *
     * TODO:
     * 1. Log in first (set up a valid mock + call authService.login)
     * 2. Call authService.logout()
     * 3. assertFalse(authService.isLoggedIn())
     * 4. assertNull(authService.getCurrentUser())
     */
    @Test
    void testLogout_clearsCurrentUser() {
        // TODO: implement test
    }
}
