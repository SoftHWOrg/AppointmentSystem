package org.example.service;

import org.example.domain.entity.Schedule;
import org.example.domain.valueobject.TimeSlot;

import java.util.List;

/**
 * Manages time slot availability for the schedule.
 * Covers US1.3 — View available appointment slots.
 *
 * @author
 * @version 1.0
 */
public class ScheduleService {

    // TODO: Add field: Schedule schedule
    // TODO: Add field: AppointmentRepository appointmentRepository
    //        (needed to cross-check which slots are booked in DB)

    /**
     * Constructor — inject dependencies.
     *
     * TODO:
     *  - Assign schedule field
     *  - Initialize schedule by loading time slots from the database
     *
     * @param schedule the schedule object holding all slots
     */
    public ScheduleService(Schedule schedule) {
        // TODO: this.schedule = schedule;
    }

    /**
     * Returns all available (unbooked) time slots (US1.3).
     *
     * TODO:
     *  - Call schedule.getAvailableSlots()
     *  - Return the list (only slots where isAvailable() == true)
     *
     * @return list of available {@link TimeSlot} objects
     */
    public List<TimeSlot> getAvailableSlots() {
        // TODO: return schedule.getAvailableSlots();
        return null;
    }

    /**
     * Adds a new time slot to the schedule (admin action).
     *
     * TODO:
     *  - Validate the slot is not null
     *  - Validate startTime is before endTime
     *  - Call schedule.addSlot(slot)
     *  - Also persist it to the database (INSERT INTO time_slots)
     *
     * @param slot the new time slot to add
     */
    public void addSlot(TimeSlot slot) {
        // TODO: implement
    }

    /**
     * Marks a slot as booked — called after a successful appointment booking.
     *
     * TODO:
     *  - Call schedule.markSlotAsBooked(slotId)
     *  - Also UPDATE time_slots SET is_available = false WHERE id = slotId in DB
     *
     * @param slotId the ID of the slot to mark as booked
     */
    public void bookSlot(int slotId) {
        // TODO: implement
    }

    /**
     * Frees a slot — called when an appointment is cancelled (US4.1).
     *
     * TODO:
     *  - Call schedule.freeSlot(slotId)
     *  - Also UPDATE time_slots SET is_available = true WHERE id = slotId in DB
     *
     * @param slotId the ID of the slot to free
     */
    public void freeSlot(int slotId) {
        // TODO: implement
    }
}
