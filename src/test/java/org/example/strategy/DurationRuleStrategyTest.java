package org.example.strategy;

import org.example.domain.appointment.UrgentAppointment;
import org.example.domain.appointment.GroupAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DurationRuleStrategyTest {

    
    @BeforeEach
    void setUp() {

    }

    
    @Test
    void testIsValid_urgentWithin30min_returnsTrue() {

    }

    
    @Test
    void testIsValid_urgentExceeds30min_returnsFalse() {

    }

    
    @Test
    void testIsValid_groupWithin120min_returnsTrue() {

    }
}
