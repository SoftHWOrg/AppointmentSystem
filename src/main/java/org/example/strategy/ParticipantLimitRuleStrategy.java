package org.example.strategy;

import org.example.domain.entity.Appointment;

public class ParticipantLimitRuleStrategy implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment) {
        if (appointment == null) return false;
        return appointment.getParticipants() <= appointment.getMaxParticipants();
    }

    @Override
    public String getErrorMessage() {
        return "The number of participants exceeds the maximum allowed for this appointment type.";
    }
}
