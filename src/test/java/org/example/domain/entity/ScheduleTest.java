package org.example.domain.entity;

import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    private Schedule schedule;
    private TimeSlot slot1;
    private TimeSlot slot2;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();
        slot1 = new TimeSlot(1, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), true);
        slot2 = new TimeSlot(2, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), true);
    }

    @Test
    void testAddSlot() {
        schedule.addSlot(slot1);
        assertEquals(1, schedule.getTimeSlots().size());
        assertTrue(schedule.getTimeSlots().contains(slot1));
    }

    @Test
    void testAddNullSlot_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> schedule.addSlot(null));
    }

    @Test
    void testGetAvailableSlots() {
        schedule.addSlot(slot1);
        schedule.addSlot(slot2);
        slot2.setAvailable(false);

        List<TimeSlot> availableSlots = schedule.getAvailableSlots();
        assertEquals(1, availableSlots.size());
        assertTrue(availableSlots.contains(slot1));
        assertFalse(availableSlots.contains(slot2));
    }

    @Test
    void testMarkSlotAsBooked() {
        schedule.addSlot(slot1);
        schedule.markSlotAsBooked(1);
        assertFalse(slot1.isAvailable());
    }

    @Test
    void testMarkNonExistentSlotAsBooked() {
        schedule.addSlot(slot1);
        schedule.markSlotAsBooked(99);
        assertTrue(slot1.isAvailable());
    }

    @Test
    void testFreeSlot() {
        slot1.setAvailable(false);
        schedule.addSlot(slot1);
        schedule.freeSlot(1);
        assertTrue(slot1.isAvailable());
    }

    @Test
    void testFreeNonExistentSlot() {
        slot1.setAvailable(false);
        schedule.addSlot(slot1);
        schedule.freeSlot(99);
        assertFalse(slot1.isAvailable());
    }
}
