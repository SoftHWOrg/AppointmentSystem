package org.example.strategy;

import org.example.domain.appointment.IndividualAppointment;
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

class ParticipantLimitRuleStrategyTest {

    
    @BeforeEach
    void setUp() {

    }

    
    @Test
    void testIsValid_individual1Participant_returnsTrue() {

    }

    
    @Test
    void testIsValid_individualExceedsLimit_returnsFalse() {

    }

    
    @Test
    void testIsValid_groupWithin20_returnsTrue() {

    }

    
    @Test
    void testIsValid_groupExceeds20_returnsFalse() {

    }
}
