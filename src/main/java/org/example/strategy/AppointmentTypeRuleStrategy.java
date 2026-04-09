package org.example.strategy;

import org.example.domain.entity.Appointment;
import org.example.domain.enums.AppointmentType;

public class AppointmentTypeRuleStrategy implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment) {
        if (appointment == null) return false;
        // Allows booking by default until specific rules are applied
        return true; 
    }

    @Override
    public String getErrorMessage() {
        return "Invalid appointment type settings.";
    }
}
