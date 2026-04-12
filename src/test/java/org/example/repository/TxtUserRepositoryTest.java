package org.example.repository;

import org.example.domain.entity.Administrator;
import org.example.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TxtUserRepositoryTest {

    private TxtUserRepository repository;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        Path testFile = tempDir.resolve("users_test.txt");
        Files.deleteIfExists(testFile);
        repository = new TxtUserRepository(testFile.toString());
    }

    @Test
    void testSaveAndFind() {
        User user = new User(0, "Test", "test@e.com", "p", "USER");
        repository.save(user);

        User found = repository.findByEmail("test@e.com");
        assertNotNull(found);
        assertEquals("Test", found.getName());
        assertEquals("USER", found.getRole());
    }

    @Test
    void testAdminPersistence() {
        Administrator admin = new Administrator(0, "Admin", "a@e.com", "ap");
        repository.save(admin);

        User found = repository.findById(admin.getId());
        assertTrue(found instanceof Administrator);
        assertEquals("ADMIN", found.getRole());
    }

    @Test
    void testUpdate() {
        User user = new User(0, "Old Name", "u@e.com", "p", "USER");
        repository.save(user);
        
        user.setName("New Name");
        repository.update(user);
        
        User updated = repository.findById(user.getId());
        assertEquals("New Name", updated.getName());
    }
}
