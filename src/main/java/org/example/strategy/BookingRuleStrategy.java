package org.example.strategy;

import org.example.domain.entity.Appointment;

public interface BookingRuleStrategy {

    
    boolean isValid(Appointment appointment);

    
    String getErrorMessage();
}
