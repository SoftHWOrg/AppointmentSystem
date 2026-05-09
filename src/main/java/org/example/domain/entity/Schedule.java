package org.example.domain.entity;

import org.example.domain.valueobject.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<TimeSlot> timeSlots;

    
    public Schedule() {
        this.timeSlots = new ArrayList<>();
    }

    
    public List<TimeSlot> getAvailableSlots() {
        return timeSlots.stream()
                .filter(TimeSlot::isAvailable)
                .toList();
    }

    
    public void addSlot(TimeSlot slot) {
        if (slot == null) throw new IllegalArgumentException("Slot cannot be null");
        timeSlots.add(slot);
    }

    
    public void markSlotAsBooked(int slotId) {
        for (TimeSlot slot : timeSlots) {
            if (slot.getId() == slotId) {
                slot.setAvailable(false);
                return;
            }
        }
    }

    
    public void freeSlot(int slotId) {
        for (TimeSlot slot : timeSlots) {
            if (slot.getId() == slotId) {
                slot.setAvailable(true);
                return;
            }
        }
    }

    
    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
}
