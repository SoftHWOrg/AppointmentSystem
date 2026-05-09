package org.example.strategy;

import org.example.domain.appointment.UrgentAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTypeRuleStrategyTest {

    private AppointmentTypeRuleStrategy strategy;
    private User testUser;
    private TimeSlot testSlot;

    @BeforeEach
    void setUp() {
        strategy = new AppointmentTypeRuleStrategy();
        testUser = new User(1, "Test", "test@email.com", "pass", "USER");
        testSlot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), true);
    }

    @Test
    void testIsValid_typePresent_returnsTrue() {
        Appointment appt = new UrgentAppointment(1, testUser, testSlot, AppointmentStatus.CONFIRMED, 1);
        assertTrue(strategy.isValid(appt));
    }

    @Test
    void testIsValid_nullAppointment_returnsFalse() {
        assertFalse(strategy.isValid(null));
    }

    @Test
    void testIsValid_nullType_returnsFalse() {
        Appointment mockAppt = org.mockito.Mockito.mock(Appointment.class);
        org.mockito.Mockito.when(mockAppt.getType()).thenReturn(null);
        assertFalse(strategy.isValid(mockAppt));
    }

    @Test
    void testGetErrorMessage() {
        assertEquals("An appointment type must be selected.", strategy.getErrorMessage());
    }
}
