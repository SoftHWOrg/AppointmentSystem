package org.example.strategy;

import org.example.domain.appointment.GroupAppointment;
import org.example.domain.appointment.UrgentAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DurationRuleStrategyTest {

    private DurationRuleStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new DurationRuleStrategy();
    }

    @Test
    void testIsValid_urgentWithin30min_returnsTrue() {
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9,0), LocalTime.of(9,30), true);
        Appointment appt = new UrgentAppointment(1, null, slot, AppointmentStatus.PENDING, 1);
        assertTrue(strategy.isValid(appt));
    }

    @Test
    void testIsValid_urgentExceeds30min_returnsFalse() {
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9,0), LocalTime.of(9,45), true);
        Appointment appt = new UrgentAppointment(1, null, slot, AppointmentStatus.PENDING, 1);
        assertFalse(strategy.isValid(appt));
    }

    @Test
    void testIsValid_groupWithin120min_returnsTrue() {
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9,0), LocalTime.of(10,30), true);
        Appointment appt = new GroupAppointment(1, null, slot, AppointmentStatus.PENDING, 5);
        assertTrue(strategy.isValid(appt));
    }
}
