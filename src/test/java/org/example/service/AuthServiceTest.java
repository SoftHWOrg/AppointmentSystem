package org.example.service;

import org.example.domain.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;

    static class StubUserRepository implements UserRepository {
        private User storedUser;

        void setUserToReturn(User user) {
            this.storedUser = user;
        }

        @Override
        public User findByEmail(String email) {
            if (storedUser != null && storedUser.getEmail().equals(email)) {
                return storedUser;
            }
            return null;
        }

        @Override
        public void save(User user) {
        }

        @Override
        public User findById(int id) {
            return null;
        }

        @Override
        public void update(User user) {
        }
    }

    private StubUserRepository stubRepo;

    @BeforeEach
    void setUp() {
        stubRepo = new StubUserRepository();
        authService = new AuthService(stubRepo);
    }

    @Test
    void testLogin_validCredentials_returnsUser() {
        User fakeUser = new User(1, "Admin User", "admin@email.com", "pass123", "ADMIN");
        stubRepo.setUserToReturn(fakeUser);

        User result = authService.login("admin@email.com", "pass123");

        assertNotNull(result);
        assertEquals("Admin User", result.getName());
        assertTrue(authService.isLoggedIn());
        assertEquals(fakeUser, authService.getCurrentUser());
    }

    @Test
    void testLogin_invalidPassword_throwsException() {
        User fakeUser = new User(1, "Admin User", "admin@email.com", "correct", "ADMIN");
        stubRepo.setUserToReturn(fakeUser);

        assertThrows(IllegalArgumentException.class, () -> {
            authService.login("admin@email.com", "wrongpassword");
        });
    }

    @Test
    void testLogin_unknownEmail_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authService.login("unknown@email.com", "anypass");
        });
    }

    @Test
    void testLogout_clearsCurrentUser() {
        User fakeUser = new User(1, "Admin", "admin@email.com", "pass", "ADMIN");
        stubRepo.setUserToReturn(fakeUser);
        authService.login("admin@email.com", "pass");
        assertTrue(authService.isLoggedIn());

        authService.logout();

        assertFalse(authService.isLoggedIn());
        assertNull(authService.getCurrentUser());
    }

    @Test
    void testIsAdmin_checksRoleCorrectly() {
        User admin = new User(1, "A", "a@e.com", "p", "ADMIN");
        stubRepo.setUserToReturn(admin);
        authService.login("a@e.com", "p");
        assertTrue(authService.isAdmin());

        authService.logout();

        User user = new User(2, "U", "u@e.com", "p", "USER");
        stubRepo.setUserToReturn(user);
        authService.login("u@e.com", "p");
        assertFalse(authService.isAdmin());
    }
}

