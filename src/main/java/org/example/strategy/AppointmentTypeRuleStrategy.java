package org.example.strategy;

import org.example.domain.entity.Appointment;
import org.example.domain.enums.AppointmentType;

public class AppointmentTypeRuleStrategy implements BookingRuleStrategy {

    
    @Override
    public boolean isValid(Appointment appointment) {

        return false;
    }

    
    @Override
    public String getErrorMessage() {

        return "";
    }
}
