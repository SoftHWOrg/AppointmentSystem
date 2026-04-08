package org.example.domain.entity;

import org.example.domain.valueobject.TimeSlot;

import java.util.List;

/**
 * Represents the full schedule — a collection of time slots.
 * Used to check availability and manage bookable time windows (US1.3).
 *
 * @author
 * @version 1.0
 */
public class Schedule {

    // TODO: Add field: List<TimeSlot> timeSlots  (all slots in this schedule)

    /**
     * Default constructor.
     * TODO: Initialize the timeSlots list as an empty ArrayList.
     */
    public Schedule() {
        // TODO: this.timeSlots = new ArrayList<>();
    }

    /**
     * Returns only the available (unbooked) time slots.
     * Used in US1.3 — View available appointment slots.
     *
     * TODO:
     *  - Iterate over timeSlots
     *  - Filter and return only those where isAvailable() == true
     *  - Use a stream or a loop
     *
     * @return list of available {@link TimeSlot} objects
     */
    public List<TimeSlot> getAvailableSlots() {
        // TODO: implement
        return null;
    }

    /**
     * Adds a new time slot to the schedule.
     *
     * TODO:
     *  - Validate that the slot is not null
     *  - Add it to the timeSlots list
     *
     * @param slot the {@link TimeSlot} to add
     */
    public void addSlot(TimeSlot slot) {
        // TODO: implement
    }

    /**
     * Marks a time slot as unavailable (booked).
     * Called when an appointment is confirmed (US2.1).
     *
     * TODO:
     *  - Find the slot in timeSlots by matching the slotId
     *  - Call slot.setAvailable(false)
     *
     * @param slotId the ID of the slot to mark as booked
     */
    public void markSlotAsBooked(int slotId) {
        // TODO: implement
    }

    /**
     * Marks a time slot as available again.
     * Called when an appointment is cancelled (US4.1).
     *
     * TODO:
     *  - Find the slot in timeSlots by matching the slotId
     *  - Call slot.setAvailable(true)
     *
     * @param slotId the ID of the slot to free up
     */
    public void freeSlot(int slotId) {
        // TODO: implement
    }

    // TODO: Add getter: getTimeSlots() — returns all slots (available or not)
}
