package org.example.repository;

import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TxtTimeSlotRepositoryTest {

    private static final String TEST_FILE = "data/timeslots_test.txt";
    private TxtTimeSlotRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
        repository = new TxtTimeSlotRepository(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    void testSaveAndFindAll() {
        TimeSlot slot = new TimeSlot(0, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), true);
        repository.save(slot);

        List<TimeSlot> all = repository.findAll();
        assertEquals(1, all.size());
        assertEquals(1, all.get(0).getId()); // First ID should be 1
        assertTrue(all.get(0).isAvailable());
    }

    @Test
    void testUpdate() {
        TimeSlot slot = new TimeSlot(0, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), true);
        repository.save(slot);
        
        TimeSlot saved = repository.findAll().get(0);
        saved.setAvailable(false);
        repository.update(saved);

        List<TimeSlot> all = repository.findAll();
        assertEquals(1, all.size());
        assertFalse(all.get(0).isAvailable());
    }

    @Test
    void testNextIdWithMultipleSlots() {
        repository.save(new TimeSlot(0, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), true));
        repository.save(new TimeSlot(0, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), true));

        List<TimeSlot> all = repository.findAll();
        assertEquals(2, all.size());
        assertEquals(1, all.get(0).getId());
        assertEquals(2, all.get(1).getId());
    }

    @Test
    void testEnsureFileExists_CreatesDirectory() throws IOException {
        String deepPath = "data/nested/test_slots.txt";
        Path path = Paths.get(deepPath);
        Files.deleteIfExists(path);
        if (Files.exists(path.getParent())) {
            Files.delete(path.getParent());
        }

        new TxtTimeSlotRepository(deepPath);
        assertTrue(Files.exists(path));
        
        Files.delete(path);
        Files.delete(path.getParent());
    }

    @Test
    void testParseLine_InvalidData() {
        try {
            Files.write(Paths.get(TEST_FILE), List.of("invalid|data|line"), java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            fail("Setup failed");
        }
        
        List<TimeSlot> slots = repository.findAll();
        assertTrue(slots.isEmpty());
    }
}
