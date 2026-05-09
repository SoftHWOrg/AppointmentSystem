package org.example.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {

    @Test
    void testAdministratorConstructor() {
        Administrator admin = new Administrator(1, "Admin User", "admin@example.com", "adminpass");
        
        assertEquals(1, admin.getId());
        assertEquals("Admin User", admin.getName());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("adminpass", admin.getPassword());
        assertEquals("ADMIN", admin.getRole());
    }

    @Test
    void testDefaultConstructor() {
        Administrator admin = new Administrator();
        assertEquals("ADMIN", admin.getRole());
    }

    @Test
    void testCanManage() {
        Administrator admin = new Administrator();
        assertTrue(admin.canManage(123));
    }
}
