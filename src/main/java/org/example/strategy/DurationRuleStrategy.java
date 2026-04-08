package org.example.strategy;

import org.example.domain.entity.Appointment;

public class DurationRuleStrategy implements BookingRuleStrategy {

    
    @Override
    public boolean isValid(Appointment appointment) {

        return false;
    }

    
    @Override
    public String getErrorMessage() {

        return "";
    }
}
