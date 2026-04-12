package org.example.service;

import org.example.domain.entity.Schedule;
import org.example.domain.valueobject.TimeSlot;
import org.example.repository.TxtTimeSlotRepository;

import java.util.List;

public class ScheduleService {

    private final Schedule schedule;
    private final TxtTimeSlotRepository slotRepository;

    public ScheduleService(Schedule schedule, TxtTimeSlotRepository slotRepository) {
        this.schedule = schedule;
        this.slotRepository = slotRepository;

        syncWithRepository();
    }

    public List<TimeSlot> getAvailableSlots() {
        syncWithRepository();
        return schedule.getAvailableSlots();
    }

    private void syncWithRepository() {
        for (TimeSlot slot : slotRepository.findAll()) {
            boolean exists = false;
            for (TimeSlot s : schedule.getTimeSlots()) {
                if (s.getId() == slot.getId()) {
                    s.setAvailable(slot.isAvailable());
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                schedule.addSlot(slot);
            }
        }
    }

    public void addSlot(TimeSlot slot) {
        if (slot == null)
            throw new IllegalArgumentException("Slot cannot be null");
        if (!slot.getEndTime().isAfter(slot.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        for (TimeSlot existing : schedule.getTimeSlots()) {
            if (existing.getDate().equals(slot.getDate()) &&
                    existing.getStartTime().equals(slot.getStartTime()) &&
                    existing.getEndTime().equals(slot.getEndTime())) {
                slot.setId(existing.getId());
                return;
            }
        }

        slotRepository.save(slot); 
        schedule.addSlot(slot); 
    }

    public void bookSlot(int slotId) {
        schedule.markSlotAsBooked(slotId);
        updateSlotInFile(slotId);
    }

    public void freeSlot(TimeSlot slot) {
        if (slot == null)
            return;

        boolean found = false;
        for (TimeSlot s : schedule.getTimeSlots()) {
            if (s.getId() == slot.getId() ||
                    (s.getDate().equals(slot.getDate()) && s.getStartTime().equals(slot.getStartTime()))) {
                s.setAvailable(true);
                slotRepository.update(s);
                found = true;
                break;
            }
        }

        if (!found) {
            slot.setAvailable(true);
            addSlot(slot);
        }
    }

    public void freeSlot(int slotId) {
        schedule.freeSlot(slotId);
        updateSlotInFile(slotId);
    }

    private void updateSlotInFile(int slotId) {
        for (TimeSlot slot : schedule.getTimeSlots()) {
            if (slot.getId() == slotId) {
                slotRepository.update(slot);
                return;
            }
        }
    }
}
