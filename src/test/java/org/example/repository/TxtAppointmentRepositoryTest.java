package org.example.repository;

import org.example.domain.appointment.UrgentAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TxtAppointmentRepositoryTest {

    private TxtAppointmentRepository repository;
    private User testUser;
    private TimeSlot testSlot;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        Path testFile = tempDir.resolve("appointments_test.txt");
        Files.deleteIfExists(testFile);
        repository = new TxtAppointmentRepository(testFile.toString());
        
        testUser = new User(1, "Test User", "test@email.com", "pass", "USER");
        testSlot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9,0), LocalTime.of(10,0), true);
    }

    @Test
    void testSaveAndFindById() {
        Appointment appt = new UrgentAppointment(0, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        repository.save(appt);
        
        int savedId = appt.getId();
        assertTrue(savedId > 0);
        
        Appointment found = repository.findById(savedId);
        assertNotNull(found);
        assertEquals(savedId, found.getId());
        assertEquals(testUser.getId(), found.getUser().getId());
    }

    @Test
    void testUpdate() {
        Appointment appt = new UrgentAppointment(0, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        repository.save(appt);
        
        appt.setStatus(AppointmentStatus.CANCELLED);
        repository.update(appt);
        
        Appointment updated = repository.findById(appt.getId());
        assertEquals(AppointmentStatus.CANCELLED, updated.getStatus());
    }

    @Test
    void testDelete() {
        Appointment appt = new UrgentAppointment(0, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        repository.save(appt);
        int id = appt.getId();
        
        repository.delete(id);
        
        assertNull(repository.findById(id));
    }

    @Test
    void testFindByUserId() {
        Appointment appt1 = new UrgentAppointment(0, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        repository.save(appt1);
        
        List<Appointment> userApps = repository.findByUserId(testUser.getId());
        assertTrue(userApps.stream().allMatch(a -> a.getUser().getId() == testUser.getId()));
    }

    @Test
    void testFindAll() {
        Appointment appt1 = new UrgentAppointment(0, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        repository.save(appt1);
        
        List<Appointment> all = repository.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void testParseMalformedLine() throws IOException {
        Path testFile = tempDir.resolve("appointments_malformed.txt");
        // A line with too few parts
        Files.writeString(testFile, "1|1|Name|Email|Pass|ROLE|1|2023-10-27|10:00|11:00|true|DEFAULT|CONFIRMED\n" + // 13 parts instead of 14
                                   "invalid_line\n" +
                                   "1|1|Name|Email|Pass|ROLE|1|2023-10-27|10:00|11:00|true|INVALID_TYPE|CONFIRMED|1"); // Invalid type
        
        TxtAppointmentRepository malformedRepo = new TxtAppointmentRepository(testFile.toString());
        List<Appointment> apps = malformedRepo.findAll();
        
        // The first line should return null (too few parts), second null, third should default to DEFAULT type
        assertEquals(1, apps.size());
        assertEquals(org.example.domain.enums.AppointmentType.DEFAULT, apps.get(0).getType());
    }

    @Test
    void testDeleteNonExistent() {
        assertDoesNotThrow(() -> repository.delete(999));
    }
}
