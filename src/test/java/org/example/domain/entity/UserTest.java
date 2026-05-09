package org.example.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserConstructorAndGetters() {
        User user = new User(1, "John Doe", "john@example.com", "password123", "USER");
        
        assertEquals(1, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    void testUserSetters() {
        User user = new User();
        user.setId(2);
        user.setName("Jane Smith");
        user.setEmail("jane@example.com");
        user.setPassword("secret");
        user.setRole("ADMIN");

        assertEquals(2, user.getId());
        assertEquals("Jane Smith", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("secret", user.getPassword());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void testToString() {
        User user = new User(1, "John Doe", "john@example.com", "password123", "USER");
        String expected = "User{id=1, name='John Doe', email='john@example.com', role='USER'}";
        assertEquals(expected, user.toString());
    }
}
