package org.example.service;

import org.example.domain.entity.Schedule;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleServiceTest {

    private Schedule schedule;

    @BeforeEach
    void setUp() {
        schedule = new Schedule();

        TimeSlot availableSlot1 = new TimeSlot(1, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), true);
        TimeSlot availableSlot2 = new TimeSlot(2, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), true);
        TimeSlot bookedSlot = new TimeSlot(3, LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0), false);

        schedule.addSlot(availableSlot1);
        schedule.addSlot(availableSlot2);
        schedule.addSlot(bookedSlot);
    }

    @Test
    void testGetAvailableSlots_returnsOnlyAvailable() {
        List<TimeSlot> availableSlots = schedule.getAvailableSlots();

        assertEquals(2, availableSlots.size());

        for (TimeSlot slot : availableSlots) {
            assertTrue(slot.isAvailable());
        }
    }

    @Test
    void testBookSlot_marksSlotUnavailable() {
        schedule.markSlotAsBooked(1);

        TimeSlot slot = schedule.getTimeSlots().stream()
                .filter(s -> s.getId() == 1)
                .findFirst()
                .orElse(null);

        assertNotNull(slot);
        assertFalse(slot.isAvailable());
    }

    @Test
    void testFreeSlot_marksSlotAvailable() {
        schedule.markSlotAsBooked(1);
        assertFalse(schedule.getTimeSlots().stream()
                .filter(s -> s.getId() == 1).findFirst().get().isAvailable());

        schedule.freeSlot(1);

        TimeSlot slot = schedule.getTimeSlots().stream()
                .filter(s -> s.getId() == 1)
                .findFirst()
                .orElse(null);

        assertNotNull(slot);
        assertTrue(slot.isAvailable());
    }
}
