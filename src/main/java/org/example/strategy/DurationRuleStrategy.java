package org.example.strategy;

import org.example.domain.entity.Appointment;

public class DurationRuleStrategy implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment) {
        if (appointment == null || appointment.getTimeSlot() == null) {
            return false;
        }
        return appointment.getTimeSlot().getDurationInMinutes() <= appointment.getMaxDuration();
    }

    @Override
    public String getErrorMessage() {
        return "Appointment duration exceeds the maximum allowed time.";
    }
}
