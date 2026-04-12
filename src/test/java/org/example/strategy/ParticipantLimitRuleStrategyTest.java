package org.example.strategy;

import org.example.domain.appointment.GroupAppointment;
import org.example.domain.appointment.IndividualAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantLimitRuleStrategyTest {

    private ParticipantLimitRuleStrategy strategy;
    private TimeSlot dummySlot;

    @BeforeEach
    void setUp() {
        strategy = new ParticipantLimitRuleStrategy();
        dummySlot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9,0), LocalTime.of(10,0), true);
    }

    @Test
    void testIsValid_individual1Participant_returnsTrue() {
        Appointment appt = new IndividualAppointment(1, null, dummySlot, AppointmentStatus.PENDING, 1);
        assertTrue(strategy.isValid(appt));
    }

    @Test
    void testIsValid_individualExceedsLimit_returnsFalse() {
        Appointment appt = new IndividualAppointment(1, null, dummySlot, AppointmentStatus.PENDING, 2);
        assertFalse(strategy.isValid(appt));
    }

    @Test
    void testIsValid_groupWithin20_returnsTrue() {
        Appointment appt = new GroupAppointment(1, null, dummySlot, AppointmentStatus.PENDING, 15);
        assertTrue(strategy.isValid(appt));
    }

    @Test
    void testIsValid_groupExceeds20_returnsFalse() {
        Appointment appt = new GroupAppointment(1, null, dummySlot, AppointmentStatus.PENDING, 25);
        assertFalse(strategy.isValid(appt));
    }

    @Test
    void testIsValid_nullCases_returnsFalse() {
        assertFalse(strategy.isValid(null));
    }

    @Test
    void testIsValid_defaultWithin5_returnsTrue() {
        Appointment appt = new org.example.domain.appointment.DefaultAppointment(1, null, dummySlot, AppointmentStatus.PENDING, 5);
        assertTrue(strategy.isValid(appt));
    }

    @Test
    void testGetErrorMessage() {
        assertEquals("The number of participants exceeds the maximum allowed for this appointment type.", strategy.getErrorMessage());
    }
}
