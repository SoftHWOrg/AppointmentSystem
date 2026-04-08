package org.example.domain.entity;

import org.example.domain.valueobject.TimeSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the full schedule — a collection of time slots.
 * Used to check availability and manage bookable time windows (US1.3).
 *
 * @author
 * @version 1.0
 */
public class Schedule {

    private List<TimeSlot> timeSlots;

    /**
     * Initialises the schedule with an empty list of time slots.
     */
    public Schedule() {
        this.timeSlots = new ArrayList<>();
    }

    /**
     * Returns only the available (unbooked) time slots.
     *
     * @return list of available TimeSlot objects
     */
    public List<TimeSlot> getAvailableSlots() {
        return timeSlots.stream()
                .filter(TimeSlot::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new time slot to the schedule.
     *
     * @param slot the TimeSlot to add
     */
    public void addSlot(TimeSlot slot) {
        if (slot == null) throw new IllegalArgumentException("Slot cannot be null");
        timeSlots.add(slot);
    }

    /**
     * Marks a time slot as unavailable (booked).
     * Called when an appointment is confirmed (US2.1).
     *
     * @param slotId the ID of the slot to mark as booked
     */
    public void markSlotAsBooked(int slotId) {
        for (TimeSlot slot : timeSlots) {
            if (slot.getId() == slotId) {
                slot.setAvailable(false);
                return;
            }
        }
    }

    /**
     * Marks a time slot as available again.
     * Called when an appointment is cancelled (US4.1).
     *
     * @param slotId the ID of the slot to free up
     */
    public void freeSlot(int slotId) {
        for (TimeSlot slot : timeSlots) {
            if (slot.getId() == slotId) {
                slot.setAvailable(true);
                return;
            }
        }
    }

    /**
     * Returns all time slots (available and booked).
     *
     * @return all time slots in this schedule
     */
    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
}
