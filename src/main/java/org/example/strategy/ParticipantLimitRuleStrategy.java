package org.example.strategy;

import org.example.domain.entity.Appointment;

public class ParticipantLimitRuleStrategy implements BookingRuleStrategy {

    
    @Override
    public boolean isValid(Appointment appointment) {

        return false;
    }

    
    @Override
    public String getErrorMessage() {

        return "";
    }
}
