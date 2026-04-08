package org.example.domain.valueobject;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a bookable time window on a specific date.
 * A time slot has a date, start time, end time, and an availability flag.
 *
 * This is a value object — it holds data, no business logic.
 *
 * @author
 * @version 1.0
 */
public class TimeSlot {

    // TODO: Add field: int id
    // TODO: Add field: LocalDate date
    // TODO: Add field: LocalTime startTime
    // TODO: Add field: LocalTime endTime
    // TODO: Add field: boolean available  (true = can be booked, false = already taken)

    /**
     * Full constructor.
     *
     * TODO: Add parameters (id, date, startTime, endTime, available)
     *       and assign each to its field.
     *
     * @param id        unique slot ID from the database
     * @param date      the date of the slot
     * @param startTime when the slot starts
     * @param endTime   when the slot ends
     * @param available whether the slot can still be booked
     */
    public TimeSlot(int id, LocalDate date, LocalTime startTime, LocalTime endTime, boolean available) {
        // TODO: assign all fields
    }

    /**
     * Calculates the duration of this slot in minutes.
     *
     * TODO:
     *  - Use java.time.Duration.between(startTime, endTime).toMinutes()
     *  - Return as int or long
     *
     * @return duration in minutes
     */
    public long getDurationInMinutes() {
        // TODO: implement
        return 0;
    }

    // TODO: Add getters and setters for all fields:
    //       getId(), getDate(), getStartTime(), getEndTime(), isAvailable()
    //       setAvailable(boolean)

    /**
     * TODO: Override toString() to return something like:
     *       "TimeSlot{date=2025-01-15, start=09:00, end=09:30, available=true}"
     */
    @Override
    public String toString() {
        // TODO: implement
        return "";
    }
}
