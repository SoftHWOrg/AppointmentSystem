package org.example.service;

import org.example.domain.entity.Schedule;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ScheduleService}.
 *
 * @author
 * @version 1.0
 */
class ScheduleServiceTest {

    // TODO: Add field: Schedule schedule
    // TODO: Add field: ScheduleService scheduleService

    /**
     * TODO:
     *  - schedule = new Schedule()
     *  - scheduleService = new ScheduleService(schedule)
     *  - Add a few TimeSlot objects to the schedule for testing
     */
    @BeforeEach
    void setUp() {
        // TODO: initialize schedule and scheduleService with test data
    }

    /**
     * US1.3 — Should return only available slots.
     *
     * TODO:
     *  1. Add 2 available slots and 1 unavailable slot to the schedule
     *  2. Call scheduleService.getAvailableSlots()
     *  3. assertEquals(2, result.size())
     *  4. assertTrue(result.stream().allMatch(TimeSlot::isAvailable))
     */
    @Test
    void testGetAvailableSlots_returnsOnlyAvailable() {
        // TODO: implement test
    }

    /**
     * US2.1 — Booking a slot should mark it as unavailable.
     *
     * TODO:
     *  1. Create a slot with id=1, isAvailable=true
     *  2. Call scheduleService.bookSlot(1)
     *  3. assertFalse(slot.isAvailable())
     */
    @Test
    void testBookSlot_marksSlotUnavailable() {
        // TODO: implement test
    }

    /**
     * US4.1 — Freeing a slot after cancellation should make it available again.
     *
     * TODO:
     *  1. Create a slot with id=1, book it first
     *  2. Call scheduleService.freeSlot(1)
     *  3. assertTrue(slot.isAvailable())
     */
    @Test
    void testFreeSlot_marksSlotAvailable() {
        // TODO: implement test
    }
}
