package org.example.service;

import org.example.domain.entity.Schedule;
import org.example.domain.valueobject.TimeSlot;
import org.example.repository.TxtTimeSlotRepository;

import java.util.List;

/**
 * Manages time slot availability for the schedule.
 * Loads slots from the text file on startup and persists every change.
 *
 * @author
 * @version 1.0
 */
public class ScheduleService {

    private final Schedule schedule;
    private final TxtTimeSlotRepository slotRepository;

    /**
     * Constructs the service, loading all persisted slots into memory.
     *
     * @param schedule       the in-memory schedule object
     * @param slotRepository the text-file repository for slots
     */
    public ScheduleService(Schedule schedule, TxtTimeSlotRepository slotRepository) {
        this.schedule = schedule;
        this.slotRepository = slotRepository;

        // Load all previously saved slots into the in-memory schedule
        for (TimeSlot slot : slotRepository.findAll()) {
            schedule.addSlot(slot);
        }
    }

    /**
     * Returns all available (unbooked) time slots.
     *
     * @return list of available slots
     */
    public List<TimeSlot> getAvailableSlots() {
        return schedule.getAvailableSlots();
    }

    /**
     * Adds a new time slot to the schedule and persists it to the file.
     *
     * @param slot the new time slot
     * @throws IllegalArgumentException if slot is null or end is before start
     */
    public void addSlot(TimeSlot slot) {
        if (slot == null) throw new IllegalArgumentException("Slot cannot be null");
        if (!slot.getEndTime().isAfter(slot.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        slotRepository.save(slot);   // assigns the real ID and writes to file
        schedule.addSlot(slot);      // slot now has the correct ID from the repo
    }

    /**
     * Marks a slot as booked and updates the file.
     *
     * @param slotId the slot to mark as booked
     */
    public void bookSlot(int slotId) {
        schedule.markSlotAsBooked(slotId);
        updateSlotInFile(slotId);
    }

    /**
     * Frees a slot and updates the file.
     *
     * @param slotId the slot to mark as available again
     */
    public void freeSlot(int slotId) {
        schedule.freeSlot(slotId);
        updateSlotInFile(slotId);
    }

    // Finds the slot in memory (after updating it) and writes the new state to file
    private void updateSlotInFile(int slotId) {
        for (TimeSlot slot : schedule.getTimeSlots()) {
            if (slot.getId() == slotId) {
                slotRepository.update(slot);
                return;
            }
        }
    }
}
