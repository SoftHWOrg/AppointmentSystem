package org.example.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    void testTimeSlotProperties() {
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(11, 0);
        TimeSlot slot = new TimeSlot(1, date, start, end, true);

        assertEquals(1, slot.getId());
        assertEquals(date, slot.getDate());
        assertEquals(start, slot.getStartTime());
        assertEquals(end, slot.getEndTime());
        assertTrue(slot.isAvailable());
    }

    @Test
    void testGetDurationInMinutes() {
        LocalTime start = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(11, 30);
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), start, end, true);

        assertEquals(90, slot.getDurationInMinutes());
    }

    @Test
    void testSetters() {
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.NOON, LocalTime.MIDNIGHT, true);
        LocalDate newDate = LocalDate.now().plusDays(1);
        LocalTime newStart = LocalTime.of(9, 0);
        LocalTime newEnd = LocalTime.of(10, 0);

        slot.setId(2);
        slot.setDate(newDate);
        slot.setStartTime(newStart);
        slot.setEndTime(newEnd);
        slot.setAvailable(false);

        assertEquals(2, slot.getId());
        assertEquals(newDate, slot.getDate());
        assertEquals(newStart, slot.getStartTime());
        assertEquals(newEnd, slot.getEndTime());
        assertFalse(slot.isAvailable());
    }

    @Test
    void testToString() {
        LocalDate date = LocalDate.of(2023, 10, 27);
        LocalTime start = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(11, 0);
        TimeSlot slot = new TimeSlot(1, date, start, end, true);

        String expected = "TimeSlot{date=2023-10-27, start=10:00, end=11:00, available=true}";
        assertEquals(expected, slot.toString());
    }
}
