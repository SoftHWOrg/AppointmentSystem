package org.example.strategy;

import org.example.domain.entity.Appointment;
import org.example.domain.enums.AppointmentType;

public class AppointmentTypeRuleStrategy implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment) {
        return appointment != null && appointment.getType() != null;
    }

    @Override
    public String getErrorMessage() {
        return "An appointment type must be selected.";
    }
}
