package org.example.strategy;

import org.example.domain.entity.Appointment;

public class DurationRuleStrategy implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment) {
        long slotMinutes = appointment.getTimeSlot().getDurationInMinutes();
        return slotMinutes <= appointment.getMaxDuration();
    }

    @Override
    public String getErrorMessage() {
        return "The selected time slot exceeds the maximum allowed duration for this appointment type.";
    }
}
